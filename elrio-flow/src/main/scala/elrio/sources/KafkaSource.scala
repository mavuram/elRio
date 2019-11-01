package elrio.sources

import elrio.cfg.SourceCfg
import org.apache.spark.sql.{DataFrame, SparkSession}

class KafkaSource extends Source {
  override def data(sourceInfo: SourceCfg, ss: SparkSession): DataFrame = {
    println("Kafka source is not implemented yet")
    return null
  }
}
