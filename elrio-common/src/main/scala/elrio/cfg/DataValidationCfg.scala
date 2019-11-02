package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
case class DataValidationCfg @JsonCreator() (@JsonProperty("rule_ids") ruleIds: Array[Int])
