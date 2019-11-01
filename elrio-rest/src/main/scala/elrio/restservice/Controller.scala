package elrio.restservice

import java.lang.Iterable

import elrio.cfg.FeedCfg
import javax.ws.rs.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(path = Array("/api"))
class Controller(@Autowired val service: Service) {
  @GetMapping(path = Array("/feeds"))
  def getFeeds(): Iterable[FeedCfg] = {
    service.listFeeds
  }
  @GetMapping(path = Array("/feeds/{id}"))
  def getFeed(@PathVariable feedId: String): FeedCfg = {
    service.getFeed(feedId) match {
      case Some(f) => f
      case None => throw new NotFoundException
    }
  }
  @PostMapping(path = Array("/users"))
  def createFeed(@RequestBody feed: FeedCfg): ResponseEntity[String] = {
    val id = service.createFeed(feed)
    new ResponseEntity(id, new HttpHeaders, HttpStatus.CREATED)
  }
}