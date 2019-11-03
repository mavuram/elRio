package elrio.engine

import elrio.Violations
import elrio.CfgLoader
import elrio.cfg._
import org.apache.spark.sql.Row
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.types.StructType

case class Validator(validationCfg: DataValidationCfg, rowSchema: StructType) {

  val validators: Array[RuleValidation] = validationCfg.ruleIds.flatMap(id => {
    CfgLoader.loadRule(id) match {
      case Some(rule) =>
        rule.validation match {
          case sr: SimpleRule => Some(SimpleRuleValidation(sr, rowSchema))
          case cr: CompositeRule => Some(CompositeRuleValidation(cr, rowSchema))
          case _ => None
        }
      case _ => None
    }
  })

  def apply(row: Row): (Row, List[Row]) = {
    val errors = validators.toList.flatMap(v => {
      if (v.eval(row)) None else {
        Some(errorRow(v.errStr))
      }
    })
    (row, errors)
  }

  def errorRow(msg: String): Row = {
    new GenericRowWithSchema(Array("this feed", 10001, msg), Violations.summarySchema)
  }
}

trait RuleValidation {
  def eval(row: Row): Boolean
  def errStr: String
}

case class SimpleRuleValidation(sr: SimpleRule, rowSchema: StructType) extends RuleValidation() {
  private val indexedCols = rowSchema.fields.zipWithIndex
  private def getCFun(fld: AnyRef) : Row =>Any = {
    fld match {
      case cname: String =>
        if (cname startsWith "$") {
          val col = indexedCols.find(p => p._1.name equalsIgnoreCase cname.substring(1))
          col match {
            case Some(c) => columnGetterFunction(c._2, null).apply
            case None => null //TODO: handle cases where column not found in the schema
          }
        } else
          columnGetterFunction(-1, cname).apply
      case o => columnGetterFunction(-1, o).apply
    }
  }

  private val operandFun = getCFun(sr.getOperand)
  private val valFuns = sr.getVals.map( v => getCFun(v))

  override def eval(row: Row): Boolean = {
    val params = Seq(operandFun(row).asInstanceOf[String]) ++: valFuns.map(vf => vf(row).asInstanceOf[String])
    sr.eval(params)
  }

  override def errStr: String = sr.getOp.toString
}

case class CompositeRuleValidation(cr: CompositeRule, rowSchema: StructType) extends RuleValidation() {
  private val ruleFs = cr.getVals.map {
    case sr: SimpleRule => SimpleRuleValidation(sr, rowSchema)
    case cr2: CompositeRule => CompositeRuleValidation(cr2, rowSchema)
  }
  override def errStr: String = cr.getOp.toString
  override def eval(row: Row): Boolean = {
    cr match {
      case _:Or => ruleFs(0).eval(row) || ruleFs(1).eval(row)
      case _:And => ruleFs(0).eval(row) && ruleFs(1).eval(row)
      case _:Not => !ruleFs(0).eval(row)
      case _ => false
    }
  }
}

//TODO: make it typed
case class columnGetterFunction(cidx: Int, v: Any){
  def apply(row: Row): Any = {
    if(cidx < 0) v else  row.get(cidx)
  }
}