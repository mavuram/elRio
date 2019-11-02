package elrio.cfg

import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest._

class DataValidationCfgTest extends FlatSpec{

  def jo(str: String): ValidationRule = {
    mapper.readValue(str, classOf[ValidationRule])
  }

  val mapper = new ObjectMapper()
  behavior of "json parser"
  it should "parse valid object for simple rule" in {
    val obj = jo("""{"op":"EQ", "operand": "xyz", "val":"Foo"}""")
    assert(obj != null)
    assert(obj.isInstanceOf[Eq])
    assert(obj.asInstanceOf[Eq].value.equals("Foo"))

    assert(jo("""{"op":"NE",  "operand": "xyz", "val":"Foo"}""").asInstanceOf[Ne].value.equals("Foo"))
    assert(jo("""{"op":"LT",  "operand": "xyz", "val":"Foo"}""").asInstanceOf[Lt].value.equals("Foo"))
    assert(jo("""{"op":"LTE", "operand": "xyz", "val":"Foo"}""").asInstanceOf[Lte].value.equals("Foo"))
    assert(jo("""{"op":"GT",  "operand": "xyz", "val":"Foo"}""").asInstanceOf[Gt].value.equals("Foo"))
    assert(jo("""{"op":"GTE", "operand": "xyz", "val":"Foo"}""").asInstanceOf[Gte].value.equals("Foo"))
    val in = jo("""{"op":"IN", "operand": "xyz", "from":"Foo", "to":"Bar"}""").asInstanceOf[In]
    assert(in.from.equals("Foo") && in.to.equals("Bar"))

    val inx = jo("""{"op":"INX", "operand": "xyz", "from":"Foo", "to":"Bar"}""").asInstanceOf[Inx]
    assert(inx.from.equals("Foo") && inx.to.equals("Bar"))

    val inxt = jo("""{"op":"INXTo", "operand": "xyz", "from":"Foo", "to":"Bar"}""").asInstanceOf[InxTo]
    assert(inxt.from.equals("Foo") && inxt.to.equals("Bar"))

    val inxr = jo("""{"op":"INXFrom", "operand": "xyz", "from":"Foo", "to":"Bar"}""").asInstanceOf[InxFrom]
    assert(inxr.from.equals("Foo") && inxr.to.equals("Bar") && inxr.operand.equals("xyz"))
  }

  it should "parse valid object composite rules" in {
    val r = jo(
      """
        |{"op":"OR", "lrule":{"op":"INXTo", "operand": "xyz", "from":"Foo", "to":"Bar"},
        |            "rrule":{"op":"GTE", "operand": "xyz", "val":"Foo"}}""".stripMargin)
    assert(r.isInstanceOf[Or])
    assert(r.asInstanceOf[Or].lRule.isInstanceOf[InxTo])
    assert(r.asInstanceOf[Or].rRule.isInstanceOf[Gte])
    assert(r.asInstanceOf[Or].lRule.asInstanceOf[InxTo].from.equals("Foo"))
    assert(r.asInstanceOf[Or].lRule.asInstanceOf[InxTo].to.equals("Bar"))
  }
}
