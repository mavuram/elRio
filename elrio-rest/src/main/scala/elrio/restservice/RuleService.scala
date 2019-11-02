package elrio.restservice

import java.lang

import elrio.cfg.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype

@stereotype.Service
class RuleService(@Autowired private val repository: RuleFileRepository) {
  def listRules(): lang.Iterable[Rule] = {
    repository.findAll
  }
  def getRule(ruleId: Int): Option[Rule] = {
    repository.findByIdo(ruleId)
  }
  def createRule(rule: Rule): Int = {
    repository.save(rule)
    rule.ruleId
  }
}
