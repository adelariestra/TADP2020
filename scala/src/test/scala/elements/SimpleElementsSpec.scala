package elements

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo

class SimpleElementsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente un triángulo" in {
    triangle("triangulo[0 @ 100, 200 @ 300, 150 @ 500]").get shouldEqual (ResultadoParseo((TriangleFigure((0, 100), (200, 300), (150, 500))), ""))
  }
  it should "parsear incorrectamente un triángulo con 2 posiciones" in {
    triangle("triangulo[0 @ 100, 200 @ 300]").isFailure shouldEqual true
  }
  it should "parsear incorrectamente un triángulo con mal tag" in {
    triangle("triangALO[0 @ 100, 200 @ 300, 150 @ 500]").isFailure shouldEqual true
  }
}
