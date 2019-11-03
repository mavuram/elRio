package elrio.engine

import com.fasterxml.jackson.databind.ObjectMapper
import elrio.cfg.DataValidationCfg
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.scalatest.FlatSpec

class RowValidatorSpec  extends FlatSpec{
  val mapper = new ObjectMapper()// with ScalaObjectMapper

  def dvc(str: String): DataValidationCfg = {
    mapper.readValue(str, classOf[DataValidationCfg])
  }

  behavior of "json validator"

  it should "load rules properly when we create a validator" in {
    val json = """{"rule_ids": [100001]}"""
    val cfg = dvc(json)

    assert(cfg.ruleIds.length == 1)
  }

  it should "use validtion rule" in {

    val conf = new SparkConf().setAppName("elRio Processing").setMaster("local")
    val ss = SparkSession.builder().config(conf).getOrCreate()
    val json = """{"rule_ids": [100001]}"""
    val cfg = dvc(json)
    val rowSchema = StructType(Array(
        StructField("emp_name", StringType, nullable=false),
        StructField("emp_id", StringType, nullable=false)
      ))
    val rows = Array(new GenericRowWithSchema(Array("Joe", "1100000"), rowSchema),
                     new GenericRowWithSchema(Array("Max", "2200000"), rowSchema),
                     new GenericRowWithSchema(Array("Jim", "4400000"), rowSchema))

    val sc = ss.sparkContext
    import ss.implicits._

    val rawData = List(("Joe", "1100000"), ("Jim", "2200000"), ("Max", "4400000"))
    val df = rawData.toDF("emp_name", "emp_id")
    val validator = Validator(cfg, rowSchema)

//    implicit val rowEncoder = RowEncoder(rowSchema)
//    val encoder = Encoders.tuple(rowEncoder, rowEncoder)

//    val results = df.map( row =>{
//      validator(row)
//    })(encoder)
    //TODO: make the below work with map instead of foreach
    df.foreach(row => {
      val drow = validator(row)
      print(drow._1, "::")
      drow._2.foreach( eRow => print(eRow, ":") )
      println()
    })
  }
}
