package elrio.cfg

import org.scalatest.FlatSpec

class ValidationSpec  extends FlatSpec{
  behavior of "validation logic validator"

  it should "return correct results for in family of conditions" in {
    def in = In("IN", "1100", "1000", "2000")
    def inx = Inx("IN", "1100", "1000", "2000")
    def inxf = InxFrom("IN", "1100", "1000", "2000")
    def inxt = InxTo("IN", "1100", "1000", "2000")

    assert(!in.eval(Seq("1099", "1100", "2000")))
    assert( in.eval(Seq("1100", "1100", "2000")))
    assert( in.eval(Seq("1110", "1100", "2000")))
    assert( in.eval(Seq("1999", "1100", "2000")))
    assert( in.eval(Seq("2000", "1100", "2000")))
    assert(!in.eval(Seq("2100", "1100", "2000")))

    assert(!inx.eval(Seq("1099", "1100", "2000")))
    assert(!inx.eval(Seq("1100", "1100", "2000")))
    assert( inx.eval(Seq("1110", "1100", "2000")))
    assert( inx.eval(Seq("1999", "1100", "2000")))
    assert(!inx.eval(Seq("2000", "1100", "2000")))
    assert(!inx.eval(Seq("2100", "1100", "2000")))

    assert(!inxf.eval(Seq("1099", "1100", "2000")))
    assert(!inxf.eval(Seq("1100", "1100", "2000")))
    assert( inxf.eval(Seq("1110", "1100", "2000")))
    assert( inxf.eval(Seq("1999", "1100", "2000")))
    assert( inxf.eval(Seq("2000", "1100", "2000")))
    assert(!inxf.eval(Seq("2100", "1100", "2000")))

    assert(!inxt.eval(Seq("1099", "1100", "2000")))
    assert(inxt.eval(Seq("1100", "1100", "2000")))
    assert( inxt.eval(Seq("1110", "1100", "2000")))
    assert( inxt.eval(Seq("1999", "1100", "2000")))
    assert(!inxt.eval(Seq("2000", "1100", "2000")))
    assert(!inxt.eval(Seq("2100", "1100", "2000")))
  }
  it should "return correct results for logical operators" in {
    def t = Eq("EQ", "1000", "1000")
    def f = Ne("NE", "1000", "1000")
    def seq = Seq("1000", "1000")
    assert(t.eval(seq))
    assert(!f.eval(seq))

    assert(Or("Or", t, f).eval(seq))
    assert(Or("Or", f, t).eval(seq))
    assert(Or("Or", t, t).eval(seq))
    assert(!Or("Or", f, f).eval(seq))

    assert(!And("And", t, f).eval(seq))
    assert(!And("And", f, t).eval(seq))
    assert(And("And", t, t).eval(seq))
    assert(!And("And", f, f).eval(seq))

    assert(!Not("And", t).eval(seq))
    assert(Not("And", f).eval(seq))
  }
}
