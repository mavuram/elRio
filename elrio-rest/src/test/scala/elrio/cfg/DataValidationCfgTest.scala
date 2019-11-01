package elrio.cfg

import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest._

class DataValidationCfgTest extends FlatSpec{

  def jo(str: String): DataValidationCfg = {
    mapper.readValue(str, classOf[DataValidationCfg])
  }

  val mapper = new ObjectMapper()
  behavior of "json parser"
  it should "parse valid object for empty " in {
//    assert(mapper.readValue("{}", classOf[DataValidationCfg]) != null)
//    assert(mapper.readValue("""{"rules":[]}""", classOf[DataValidationCfg]) != null)
    val obj = mapper.readValue("""{"rules":[]}""", classOf[DataValidationCfg])
    assert(obj != null)
    assert(obj.rules != null)
    assert(obj.rules.length == 0)
  }
  it should "parse valid object for simple rule" in {
    val obj = jo("""{"rules":[{"op":"EQ", "operand": "xyz", "val":"Foo"}]}""")
    assert(obj != null)
    assert(obj.rules != null)
    assert(obj.rules.length == 1)
    assert(obj.rules(0) != null)
    assert(obj.rules(0).isInstanceOf[Eq])
    assert(obj.rules(0).asInstanceOf[Eq].value.equals("Foo"))

    assert(jo("""{"rules":[{"op":"NE",  "operand": "xyz", "val":"Foo"}]}""").rules(0).asInstanceOf[Ne].value.equals("Foo"))
    assert(jo("""{"rules":[{"op":"LT",  "operand": "xyz", "val":"Foo"}]}""").rules(0).asInstanceOf[Lt].value.equals("Foo"))
    assert(jo("""{"rules":[{"op":"LTE", "operand": "xyz", "val":"Foo"}]}""").rules(0).asInstanceOf[Lte].value.equals("Foo"))
    assert(jo("""{"rules":[{"op":"GT",  "operand": "xyz", "val":"Foo"}]}""").rules(0).asInstanceOf[Gt].value.equals("Foo"))
    assert(jo("""{"rules":[{"op":"GTE", "operand": "xyz", "val":"Foo"}]}""").rules(0).asInstanceOf[Gte].value.equals("Foo"))
    val in = jo("""{"rules":[{"op":"IN", "operand": "xyz", "from":"Foo", "to":"Bar"}]}""").rules(0).asInstanceOf[In]
    assert(in.from.equals("Foo") && in.to.equals("Bar"))

    val inx = jo("""{"rules":[{"op":"INX", "operand": "xyz", "from":"Foo", "to":"Bar"}]}""").rules(0).asInstanceOf[Inx]
    assert(inx.from.equals("Foo") && inx.to.equals("Bar"))

    val inxt = jo("""{"rules":[{"op":"INXTo", "operand": "xyz", "from":"Foo", "to":"Bar"}]}""").rules(0).asInstanceOf[InxTo]
    assert(inxt.from.equals("Foo") && inxt.to.equals("Bar"))

    val inxr = jo("""{"rules":[{"op":"INXFrom", "operand": "xyz", "from":"Foo", "to":"Bar"}]}""").rules(0).asInstanceOf[InxFrom]
    assert(inxr.from.equals("Foo") && inxr.to.equals("Bar") && inxr.operand.equals("xyz"))
  }

  it should "parse valid object for multiple rules" in {
    val r = jo(
      """
        |{"rules":[
        |  {"op":"INXFrom", "operand": "xyz", "from":"Foo", "to":"Bar"},
        |  {"op":"GTE", "operand": "xyz", "val":"Foo"}
        |]}""".stripMargin)
    assert(r.rules.length == 2)
    assert(r.rules(0).isInstanceOf[InxFrom])
    assert(r.rules(1).isInstanceOf[Gte])
  }

  it should "parse valid object composite rules" in {
    val r = jo(
      """
        |{"rules":[
        |  {"op":"OR", "lrule":{"op":"INXTo", "operand": "xyz", "from":"Foo", "to":"Bar"},
        |              "rrule":{"op":"GTE", "operand": "xyz", "val":"Foo"}},
        |  {"op":"LT", "operand": "xyz", "val":"Foo"}
        |]}""".stripMargin)
    assert(r.rules.length == 2)
    assert(r.rules(0).isInstanceOf[Or])
    assert(r.rules(0).asInstanceOf[Or].lRule.isInstanceOf[InxTo])
    assert(r.rules(0).asInstanceOf[Or].rRule.isInstanceOf[Gte])
    assert(r.rules(0).asInstanceOf[Or].lRule.asInstanceOf[InxTo].from.equals("Foo"))
    assert(r.rules(0).asInstanceOf[Or].lRule.asInstanceOf[InxTo].to.equals("Bar"))
    assert(r.rules(1).isInstanceOf[Lt])
    assert(r.rules(1).asInstanceOf[Lt].value.equals("Foo"))
  }
}
