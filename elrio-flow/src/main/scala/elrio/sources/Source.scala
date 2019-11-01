package elrio.sources

import elrio.cfg.{FileSourceCfg, KafkaSourceCfg, SourceCfg}
import org.apache.spark.sql.{DataFrame, SparkSession}

trait Source {
  def data(sourceInfo: SourceCfg, ss: SparkSession): DataFrame
}

object Source {
  def source(source: SourceCfg): Option[Source] ={
    source match {
      case fs: FileSourceCfg => Some(new FileSource())
      case ks: KafkaSourceCfg => Some(new KafkaSource())
      case _ => None
    }
  }
}
