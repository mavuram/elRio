package elrio

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

class Violations

object Violations {
  val summarySchema =
    StructType(
      Array(
        StructField("feed_name", StringType, nullable=false),
        StructField("rule_id", IntegerType, nullable=false),
        StructField("message", IntegerType, nullable=false)
      )
    )

  val detailSchema =
    StructType(
      Array(
        StructField("feed_name", StringType, nullable=false),
        StructField("rule_id", IntegerType, nullable=false),
        StructField("fld_name", StringType, nullable=false),
        StructField("fld_value", StringType, nullable=false)
      )
    )
}