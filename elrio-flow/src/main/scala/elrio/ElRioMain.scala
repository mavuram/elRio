package elrio

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object ElRioMain {
  //TODO: use logging
  //TODO: handle exceptions

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("elRio Processing").setMaster("local")
    val ss = SparkSession.builder().config(conf).getOrCreate()

    val feeds = CfgLoader.loadFeeds().map(cfg => new Feed(cfg, ss))
    feeds.foreach(feed => Feed.queue(feed))
  }
}
