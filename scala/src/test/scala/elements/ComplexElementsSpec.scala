package elements

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo

class ComplexElementsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente un grupo" in {
    group("grupo(\n   \t triangulo[200 @ 50, 101 @ 335, 299 @ 335],\n   \t circulo[200 @ 350, 100]\n    )").get shouldEqual (ResultadoParseo(GroupFigure(List(TriangleFigure(Position(200, 50), Position(101, 335), Position(299, 335)), CircleFigure(Position(200, 350), 100))), ""))
  }

  it should "parsear incorrectamente un grupo si algún elementoSimple falla" in {
    group("grupo(\n   \t triangulo[200 @ 50, 101 @ 335],\n   \t circulo[200 @ 350, 100]\n    )").isFailure shouldEqual (true)
  }

  // TODO: make tests for transformation fails

  it should "parsear correctamente una escala con un componente" in {
    escala("escala[6, 15](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(EscalaTr(List(CircleFigure(Position(0, 5), 10)),6,15), ""))
  }

  it should "parsear correctamente una escala con más de un componente" in {
    escala("escala[6, 15](\n\tcirculo[0 @ 5, 10],\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(EscalaTr(List(CircleFigure(Position(0, 5), 10),CircleFigure(Position(0, 5), 10)),6,15), ""))
  }

  it should "parsear correctamente una rotacion con un componente" in {
    rotacion("rotacion[6](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(RotacionTr(List(CircleFigure(Position(0, 5), 10)),6), ""))
  }

  it should "parsear correctamente una rotacion con más de un componente" in {
    rotacion("rotacion[6](\n\tcirculo[0 @ 5, 10],\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(RotacionTr(List(CircleFigure(Position(0, 5), 10),CircleFigure(Position(0, 5), 10)),6), ""))
  }

  it should "parsear correctamente una traslación con un componente" in {
    traslacion("traslacion[6, 15](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(TraslacionTr(List(CircleFigure(Position(0, 5), 10)),6,15), ""))
  }

  it should "parsear correctamente una traslación con más de un componente" in {
    traslacion("traslacion[6, 15](\n\tcirculo[0 @ 5, 10],\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(TraslacionTr(List(CircleFigure(Position(0, 5), 10),CircleFigure(Position(0, 5), 10)),6,15), ""))
  }

  it should "parsear correctamente una color con un componente" in {
    color("color[6, 6, 6](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(ColorTr(List(CircleFigure(Position(0, 5), 10)),6,6,6), ""))
  }

  it should "parsear correctamente una color con más de un componente" in {
    color("color[6, 6, 6](\n\tcirculo[0 @ 5, 10],\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(ColorTr(List(CircleFigure(Position(0, 5), 10),CircleFigure(Position(0, 5), 10)),6,6,6), ""))
  }
}