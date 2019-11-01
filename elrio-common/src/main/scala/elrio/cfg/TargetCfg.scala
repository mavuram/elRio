package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

case class TargetCfg @JsonCreator() ( @JsonProperty("path") path: String, @JsonProperty("partition") partition: String)
