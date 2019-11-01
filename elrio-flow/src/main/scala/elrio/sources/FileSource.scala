package elrio.sources
import elrio.cfg.{FileSourceCfg, SourceCfg}
import org.apache.spark.sql.{DataFrame, SparkSession}

class FileSource extends Source {
  override def data(sourceCfg: SourceCfg, ss: SparkSession): DataFrame = {
    val filePath = sourceCfg.asInstanceOf[FileSourceCfg].path
    println("processing file "+filePath)
//    val rdd = sc.textFile(filePath)
//    rdd.map( r => Row.fromSeq(r.split(",")))
    ss.read.option("header", "true").csv(filePath)
  }
}

object FileSource {
  val SOURCE: String = "file"
}
