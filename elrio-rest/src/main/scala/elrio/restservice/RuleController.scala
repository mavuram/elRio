package elrio.restservice

import java.lang.Iterable

import elrio.cfg.Rule
import javax.ws.rs.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(path = Array("/api"))
class RuleController(@Autowired val service: RuleService) {
  @GetMapping(path = Array("/rules"))
  def getFeeds: Iterable[Rule] = {
    service.listRules()
  }
  @GetMapping(path = Array("/rules/{id}"))
  def getFeed(@PathVariable ruleId: Int): Rule = {
    service.getRule(ruleId) match {
      case Some(f) => f
      case None => throw new NotFoundException
    }
  }
  @PostMapping(path = Array("/rules"))
  def createFeed(@RequestBody rule: Rule): ResponseEntity[Int] = {
    val id = service.createRule(rule)
    new ResponseEntity(id, new HttpHeaders, HttpStatus.CREATED)
  }
}