package elrio.restservice

import java.lang

import elrio.CfgLoder
import elrio.cfg.Rule
import org.springframework.data.repository
import org.springframework.stereotype.Repository

import scala.collection.JavaConverters._

@Repository
class RuleFileRepository extends repository.Repository[Rule, Int]{
    def save(rule: Rule) : Unit = {
    }

  def findByIdo(ruleId: Int): Option[Rule] = {
    CfgLoder.loadRule(ruleId)
  }
  def findAll: lang.Iterable[Rule] = {
    CfgLoder.loadRules().asJava
  }
}
