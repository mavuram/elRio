package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty, JsonSubTypes, JsonTypeInfo}
case class DataValidationCfg @JsonCreator() (@JsonProperty("rules") rules: Array[Rule])

object Op extends Enumeration {
  type Op = Value
  val EQ, NE, LT, LTE, GT, GTE, IN, INX, INXTo, INXFrom, AND, OR, NOT = Value
}

import elrio.cfg.Op._

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "op")
@JsonSubTypes(Array(
  new JsonSubTypes.Type(value = classOf[Eq],      name = "EQ"),
  new JsonSubTypes.Type(value = classOf[Ne],      name = "NE"),
  new JsonSubTypes.Type(value = classOf[Lt],      name = "LT"),
  new JsonSubTypes.Type(value = classOf[Lte],     name = "LTE"),
  new JsonSubTypes.Type(value = classOf[Gt],      name = "GT"),
  new JsonSubTypes.Type(value = classOf[Gte],     name = "GTE"),
  new JsonSubTypes.Type(value = classOf[In],      name = "IN"),
  new JsonSubTypes.Type(value = classOf[Inx],     name = "INX"),
  new JsonSubTypes.Type(value = classOf[InxTo],   name = "INXTo"),
  new JsonSubTypes.Type(value = classOf[InxFrom], name = "INXFrom"),
  new JsonSubTypes.Type(value = classOf[Or],      name = "OR"),
  new JsonSubTypes.Type(value = classOf[And],     name = "AND"),
  new JsonSubTypes.Type(value = classOf[Not],     name = "NOT")
))
abstract class  Rule(op: Op, vals: Array[AnyRef])

abstract class SimpleRule(op: Op, operand: String, vals: Array[AnyRef]) extends Rule(op, vals)
abstract class CompositeRule(op: Op, vals: Array[AnyRef]) extends Rule(op, vals)

case class Eq @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef)                                  extends SimpleRule(EQ, operand, Array(value))
case class Ne @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef)                                  extends SimpleRule(NE, operand, Array(value))
case class Lt @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef)                                  extends SimpleRule(LT, operand, Array(value))
case class Lte @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef)                                  extends SimpleRule(LTE, operand, Array(value))
case class Gt @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef)                                  extends SimpleRule(GT, operand, Array(value))

case class Gte @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("val") value: AnyRef)                                  extends SimpleRule(GTE, operand, Array(value))
case class In @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef)  extends SimpleRule(IN, operand, Array(from, to))
case class Inx @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef)  extends SimpleRule(INX, operand, Array(from, to))
case class InxTo @JsonCreator()     (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef)  extends SimpleRule(INXTo, operand, Array(from, to))
case class InxFrom @JsonCreator()   (@JsonProperty("op") op: String, @JsonProperty("operand") operand: String, @JsonProperty("from") from: AnyRef, @JsonProperty("to") to: AnyRef)  extends SimpleRule(INXFrom, operand, Array(from, to))

case class Or @JsonCreator()        (@JsonProperty("op") op: String, @JsonProperty("lrule") lRule: Rule, @JsonProperty("rrule") rRule: Rule)   extends CompositeRule(OR, Array(lRule, rRule))
case class And @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("lrule") lRule: Rule, @JsonProperty("rrule") rRule: Rule)   extends CompositeRule(AND, Array(lRule, rRule))
case class Not @JsonCreator()       (@JsonProperty("op") op: String, @JsonProperty("rule") rule: Rule)                                         extends CompositeRule(NOT, Array(rule))
