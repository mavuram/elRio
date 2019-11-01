package elrio

import elrio.cfg.FeedCfg
import elrio.sources.Source
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions.col

class Feed(cfg: FeedCfg, sparkSession: SparkSession) {
  def process(): Unit = {
    val src = Source.source(cfg.source)

    //TODO??: process using getOrElse
    val data = src match {
      case Some(s) => processData(s.data(cfg.source, sparkSession))
      case _ => Unit
    }
  }

  def processData(df: DataFrame): Unit ={
    val t1 = System.currentTimeMillis()
    //TODO: handle null better
    if(cfg.projections != null) {
      cfg.projections.foreach(proj => {

        val dfw = if(proj.columns == null || proj.columns.size == 0) df
                  else df.select(proj.columns.map( c => col(c)):_*)
        val dfm = dfw.write.mode(SaveMode.Overwrite)
        if(proj.target.partition != null)
          dfm.partitionBy(proj.target.partition).save(proj.target.path)
        else
          dfm.save(proj.target.path)
        println("created file " + proj.target.path)
      })
    }
    val t2 = System.currentTimeMillis()
    println(cfg.name+" took "+(t2-t1)+" millis")
//    val row = df.take(1)
//    val sch = df.schema
//    row.foreach(c => {
//      for(i <- 0 until c.length)
//        print(sch.fields(i).name+":"+c.get(i)+", ")
//    })
//    println()
//    println("got rows "+df.count())
  }
}

object Feed{
  def queue(feed: Feed): Unit = {
    //TODO: post to a processing queue
    feed.process()
  }
}
