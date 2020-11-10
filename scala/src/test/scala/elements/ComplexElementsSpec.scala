package elements

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo

class ComplexElementsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente un grupo1" in {
    group("grupo(\n   \t triangulo[200 @ 50, 101 @ 335, 299 @ 335], circulo[200 @ 350, 100]\n    )").get shouldEqual (ResultadoParseo(GroupFigure(List(TriangleFigure(Position(200,50),Position(101,335),Position(299,335)), CircleFigure(Position(200,350),100))), ""))
  }
}
