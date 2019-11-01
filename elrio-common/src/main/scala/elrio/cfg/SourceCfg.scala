package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty, JsonSubTypes, JsonTypeInfo}

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "source_type")
@JsonSubTypes(Array(
  new JsonSubTypes.Type(value = classOf[FileSourceCfg], name = "file"),
  new JsonSubTypes.Type(value = classOf[KafkaSourceCfg], name = "kafka")
))
abstract class SourceCfg @JsonCreator()(@JsonProperty("source_type") sourceType: String,
                                         @JsonProperty("format") format: FormatCfg){}


case class FileSourceCfg @JsonCreator()(@JsonProperty("source_type") sourceType: String,
                                        @JsonProperty("path") path: String,
                                        @JsonProperty("format") format: FormatCfg
                                       )
  extends SourceCfg(sourceType, format)


case class KafkaSourceCfg @JsonCreator()(@JsonProperty("source_type") sourceType: String,
                                        @JsonProperty("topic") path: String,
                                        @JsonProperty("format") format: FormatCfg)
  extends SourceCfg(sourceType, format)
