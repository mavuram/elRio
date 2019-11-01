package elrio.restservice

import java.lang

import elrio.cfg.FeedCfg
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype

@stereotype.Service
class Service(@Autowired private val repository: FeedFileRepository) {
  def listFeeds(): lang.Iterable[FeedCfg] = {
    repository.findAll
  }
  def getFeed(feedId: String): Option[FeedCfg] = {
    repository.findByIdo(feedId)
  }
  def createFeed(feedCfg: FeedCfg): String = {
    repository.save(feedCfg)
    feedCfg.name
  }
}
