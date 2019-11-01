package elrio

import java.io.File
import elrio.cfg.FeedCfg

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object CfgLoder extends App {
  def load(): List[FeedCfg] = {
    val feedsPath = getClass().getResource("/feeds").getPath
    val feeds = new File(feedsPath)
    val cfgFiles =
      if (feeds.exists() && feeds.isDirectory) {
        feeds.listFiles().toList.filter(f => f.getName().endsWith(".json"))
      } else {
        Nil
      }

    val mapper = new ObjectMapper()// with ScalaObjectMapper
    cfgFiles.map( f => {
      val cfg: FeedCfg = mapper.readValue(scala.io.Source.fromFile(f).reader(), classOf[FeedCfg])
      cfg.name = f.getName
      cfg
    } )
  }

  def load(feedName: String) : Option[FeedCfg] = {
    val file = new File(getClass().getResource("/feeds/"+feedName+".json").getPath)
    if(file.exists()){
      val mapper = new ObjectMapper() with ScalaObjectMapper
      mapper.readValue(scala.io.Source.fromFile(file).reader())
    }else{
      None
    }
  }

  override def main(args: Array[String]): Unit = {

    val mapper = new ObjectMapper()// with ScalaObjectMapper
    val files = Array("emp.json", "sal.json")
    val base = "C:\\Users\\Vishnu\\IdeaProjects\\elRioCore\\elrio-rest\\src\\main\\resources\\feeds\\";
    files.foreach(f => {
      println (mapper.readValue(scala.io.Source.fromFile(base + f).reader(),classOf[FeedCfg]) )
    })
  }
}
