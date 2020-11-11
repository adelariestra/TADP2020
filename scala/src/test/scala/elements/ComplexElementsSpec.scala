package elements

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo

class ComplexElementsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente un grupo" in {
    group("grupo(\n   \t triangulo[200 @ 50, 101 @ 335, 299 @ 335],\n   \t circulo[200 @ 350, 100]\n    )").get shouldEqual (ResultadoParseo(GroupFigure(List(TriangleFigure(Position(200, 50), Position(101, 335), Position(299, 335)), CircleFigure(Position(200, 350), 100))), ""))
  }

  it should "parsear incorrectamente si algún elementoSimple falla" in {
    group("grupo(\n   \t triangulo[200 @ 50, 101 @ 335],\n   \t circulo[200 @ 350, 100]\n    )").isFailure shouldEqual (true)
  }

  it should "parsear correctamente una escala con un componente" in {
    escala("escala[6, 15](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(EscalaTr(List(CircleFigure(Position(0, 5), 10)),6,15), ""))
  }

  it should "parsear correctamente una escala con más de un componente" in {
    escala("escala[6, 15](\n\tcirculo[0 @ 5, 10],\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(EscalaTr(List(CircleFigure(Position(0, 5), 10),CircleFigure(Position(0, 5), 10)),6,15), ""))
  }
}