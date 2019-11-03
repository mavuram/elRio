package elrio.restservice

import java.lang

import elrio.CfgLoader
import elrio.cfg.FeedCfg
import org.springframework.data.repository
import org.springframework.stereotype.Repository

import scala.collection.JavaConverters._

@Repository
class FeedFileRepository extends repository.Repository[FeedCfg, String]{
    def save(feedCfg: FeedCfg) : Unit = {
    }

  def findByIdo(feedId: String): Option[FeedCfg] = {
    CfgLoader.loadFeed(feedId)
  }
  def findAll: lang.Iterable[FeedCfg] = {
    CfgLoader.loadFeeds().asJava
  }
}
