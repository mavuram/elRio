package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty, JsonSubTypes, JsonTypeInfo}

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "format_type")
@JsonSubTypes(Array(
  new JsonSubTypes.Type(value = classOf[DelimitedFormatCfg], name = "delimited"),
  new JsonSubTypes.Type(value = classOf[FixedSizeFormatCfg], name = "fixed_size")
))
abstract class FormatCfg @JsonCreator()(@JsonProperty("format_type") formatType: String) {}

abstract class DelimitedFormatCfg @JsonCreator()(@JsonProperty("format_type") formatType: String,
                                             @JsonProperty("delimiter") delimiter: String,
                                             @JsonProperty("column_names_line") column_names_line: Int,
                                             @JsonProperty("skip_top_lines") skip_top_lines: Int,
                                             @JsonProperty("skip_bottom_lines") skip_bottom_lines: Int)
  extends FormatCfg(formatType)

case class Column @JsonCreator()(@JsonProperty("name") name: String, @JsonProperty("size") size: Int)

case class FixedSizeFormatCfg @JsonCreator()(@JsonProperty("format_type") formatType: String,
                                       @JsonProperty("columns") sizes: Array[Column])
  extends FormatCfg(formatType)