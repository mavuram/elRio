package elrio

import elrio.cfg.{DataValidationCfg, FeedCfg}
import elrio.sources.Source
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions.col

class Feed(cfg: FeedCfg, sparkSession: SparkSession) {
  def process(): Unit = {
    val src = Source.source(cfg.source)

    //TODO??: process using getOrElse
    val data = src match {
      case Some(s) => processData(s.data(cfg.source, sparkSession), cfg)
      case _ => Unit
    }
  }

  def processData(df: DataFrame, cfg: FeedCfg): Unit = {
    val t1 = System.currentTimeMillis()
    //TODO: handle null better
    if (cfg.projections != null) {
      cfg.projections.foreach(proj => {

        val dfw = if (proj.columns == null || proj.columns.length == 0) df
        else df.select(proj.columns.map(c => col(c)): _*)
        val dfm = dfw.write.mode(SaveMode.Overwrite)
        if (proj.target.partition != null)
          dfm.partitionBy(proj.target.partition).save(proj.target.path)
        else
          dfm.save(proj.target.path)
        println("created file " + proj.target.path)
      })
    }
    val t2 = System.currentTimeMillis()
    println(cfg.name + " took " + (t2 - t1) + " millis")
  }

  def validate(df: DataFrame, validationCfg: DataValidationCfg): (DataFrame, DataFrame) = {
    //TODO: impleent this
    if (validationCfg.ruleIds == null || validationCfg.ruleIds.length == 0)
      (df, null)
    else
      (null, null)
  }
}

object Feed{
  def queue(feed: Feed): Unit = {
    //TODO: post to a processing queue
    feed.process()
  }
}
