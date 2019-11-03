package elrio

import java.io.File

import com.fasterxml.jackson.databind.ObjectMapper
import elrio.cfg.{FeedCfg, Rule}

object CfgLoader extends App {
  //TODO: make this generic
  def loadRules(): List[Rule] = {
    val rulesPath = getClass.getResource("/rules").getPath
    val rulesDir = new File(rulesPath)
    val ruleFiles =
      if (rulesDir.exists() && rulesDir.isDirectory) {
        rulesDir.listFiles().toList.filter(f => f.getName.endsWith(".json"))
      } else {
        Nil
      }

//    val mapper = new ObjectMapper() // with ScalaObjectMapper
    ruleFiles.map(f => {
      val cfg: Rule = loadRule(f)
      cfg
    })
  }

  def loadRule(file: File): Rule = {
    val mapper = new ObjectMapper()// with ScalaObjectMapper
    mapper.readValue(scala.io.Source.fromFile(file).reader(), classOf[Rule])
  }

  def loadRule(ruleId: Int) : Option[Rule] = {
    val file = new File(getClass.getResource("/rules/"+ruleId+".json").getPath)
    if(file.exists()){
      println(file.getAbsolutePath)
//      val mapper = new ObjectMapper()// with ScalaObjectMapper
//      val cfg: Rule = mapper.readValue(scala.io.Source.fromFile(file).reader(), classOf[Rule])
      Some(loadRule(file))
    }else{
      None
    }
  }

  def loadFeeds(): List[FeedCfg] = {
    val feedsPath = getClass.getResource("/feeds").getPath
    val feeds = new File(feedsPath)
    val cfgFiles =
      if (feeds.exists() && feeds.isDirectory) {
        feeds.listFiles().toList.filter(f => f.getName.endsWith(".json"))
      } else {
        Nil
      }

//    val mapper = new ObjectMapper() // with ScalaObjectMapper
    cfgFiles.map(f => {
//      println(f.getAbsolutePath)
//      val cfg: FeedCfg = mapper.readValue(scala.io.Source.fromFile(f).reader(), classOf[FeedCfg])
//      cfg.name = f.getName
//      cfg
      loadFeedCfg(f)
    })
  }

  def loadFeedCfg(file: File): FeedCfg = {
    val mapper = new ObjectMapper()// with ScalaObjectMapper
    mapper.readValue(scala.io.Source.fromFile(file).reader(), classOf[FeedCfg])
  }
  def loadFeed(feedName: String) : Option[FeedCfg] = {
    val file = new File(getClass.getResource("/feeds/"+feedName+".json").getPath)
    if(file.exists()){
//      println(file.getAbsolutePath)
//      val mapper = new ObjectMapper()// with ScalaObjectMapper
//      val cfg: FeedCfg = mapper.readValue(scala.io.Source.fromFile(file).reader(), classOf[FeedCfg])
      Some(loadFeedCfg(file))
    }else{
      None
    }
  }

  override def main(args: Array[String]): Unit = {
    val mapper = new ObjectMapper()// with ScalaObjectMapper
    val files = Array("emp", "sal")
    files.foreach(f => {
      println(loadFeed(f))
    })
    val files2 = Array(100001)
    files2.foreach(f => {
      println(loadRule(f))
    })
  }
}
