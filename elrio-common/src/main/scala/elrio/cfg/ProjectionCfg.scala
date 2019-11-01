package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

case class ProjectionCfg @JsonCreator() ( @JsonProperty("columns") columns: Array[String],
                                          @JsonProperty("target") target: TargetCfg)
