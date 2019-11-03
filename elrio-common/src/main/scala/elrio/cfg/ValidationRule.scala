package elrio.cfg


object Op extends Enumeration {
  type Op = Value
  val EQ, NE, LT, LTE, GT, GTE, IN, INX, INXTo, INXFrom, AND, OR, NOT = Value
}

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty, JsonSubTypes, JsonTypeInfo}
import elrio.cfg.Op._

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "op")
@JsonSubTypes(Array(
  new JsonSubTypes.Type(value = classOf[Eq],      name = "EQ"),
  new JsonSubTypes.Type(value = classOf[Ne],      name = "NE"),
  new JsonSubTypes.Type(value = classOf[Lt],      name = "LT"),
  new JsonSubTypes.Type(value = classOf[Lte],     name = "LTE"),
  new JsonSubTypes.Type(value = classOf[Gt],      name = "GT"),
  new JsonSubTypes.Type(value = classOf[Gte],     name = "GTE"),
  new JsonSubTypes.Type(value = classOf[In],      name = "IN"),
  new JsonSubTypes.Type(value = classOf[Inx],     name = "INX"),
  new JsonSubTypes.Type(value = classOf[InxTo],   name = "INXTo"),
  new JsonSubTypes.Type(value = classOf[InxFrom], name = "INXFrom"),
  new JsonSubTypes.Type(value = classOf[Or],      name = "OR"),
  new JsonSubTypes.Type(value = classOf[And],     name = "AND"),
  new JsonSubTypes.Type(value = classOf[Not],     name = "NOT")
))
trait  ValidationRule{
  def getOp: Op
  def getVals: Array[AnyRef]
  def eval(seq: Seq[String]) : Boolean
}

trait SimpleRule extends  ValidationRule {
  def getOperand: String
  protected def getEvalF: Seq[String] => Boolean
  override def eval(seq: Seq[String]) : Boolean = getEvalF.apply(seq)

}
trait CompositeRule  extends ValidationRule {
}

case class Eq @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef) extends SimpleRule {
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) == 0
  override def getOp: Op = EQ
  override def getVals: Array[AnyRef] = Array(value)
}
case class Ne @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef) extends SimpleRule {
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) != 0
  override def getOp: Op = NE
  override def getVals: Array[AnyRef] = Array(value)
}
case class Lt @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef) extends SimpleRule{
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) < 0
  override def getOp: Op = LT
  override def getVals: Array[AnyRef] = Array(value)
}
case class Lte @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef) extends SimpleRule {
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) <= 0
  override def getOp: Op = LTE
  override def getVals: Array[AnyRef] = Array(value)
}
case class Gt @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef) extends SimpleRule {
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) > 0
  override def getOp: Op = GT
  override def getVals: Array[AnyRef] = Array(value)
}
case class Gte @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef) extends SimpleRule {
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) >= 0
  override def getOp: Op = GTE
  override def getVals: Array[AnyRef] = Array(value)
}

case class In @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef) extends SimpleRule{
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) >= 0 && str.head.compareTo(str(2)) <= 0
  override def getOp: Op = IN
  override def getVals: Array[AnyRef] = Array(from, to)
}
case class Inx @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef) extends SimpleRule{
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) > 0 && str.head.compareTo(str(2)) < 0
  override def getOp: Op = INX
  override def getVals: Array[AnyRef] = Array(from, to)
}
case class InxTo @JsonCreator()     (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef) extends SimpleRule{
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) >= 0 && str.head.compareTo(str(2)) < 0
  override def getOp: Op = INXTo
  override def getVals: Array[AnyRef] = Array(from, to)
}
case class InxFrom @JsonCreator()   (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef) extends SimpleRule{
  override def getOperand: String = operand
  protected def getEvalF: Seq[String] => Boolean = (str: Seq[String]) => str.head.compareTo(str(1)) > 0 && str.head.compareTo(str(2)) <= 0
  override def getOp: Op = INXFrom
  override def getVals: Array[AnyRef] = Array(from, to)
}

case class Or @JsonCreator()        (@JsonProperty("op") opStr: String, @JsonProperty("lrule") lRule: ValidationRule, @JsonProperty("rrule") rRule: ValidationRule) extends CompositeRule{
  override def getOp: Op = OR
  override def getVals: Array[AnyRef] = Array(lRule, rRule)
  override def eval(seq: Seq[String]) : Boolean = lRule.eval(seq) || rRule.eval(seq)
}
case class And @JsonCreator()       (@JsonProperty("op") opStr: String, @JsonProperty("lrule") lRule: ValidationRule, @JsonProperty("rrule") rRule: ValidationRule) extends CompositeRule{
  override def getOp: Op = AND
  override def getVals: Array[AnyRef] = Array(Array(lRule, rRule))
  override def eval(seq: Seq[String]) : Boolean = lRule.eval(seq) && rRule.eval(seq)
}
case class Not @JsonCreator()       (@JsonProperty("op") opStr: String, @JsonProperty("rule") rule: ValidationRule) extends CompositeRule {
  override def getOp: Op = NOT
  override def getVals: Array[AnyRef] = Array(rule)
  override def eval(seq: Seq[String]) : Boolean = !rule.eval(seq)
}

case class Rule @JsonCreator()(@JsonProperty("rule_id")     ruleId: Int,
                               @JsonProperty("validation")  validation: ValidationRule,
                               @JsonProperty("message")     message: String,
                               @JsonProperty("severity")    severity: String )

