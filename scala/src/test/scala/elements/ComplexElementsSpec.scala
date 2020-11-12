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
    escala("escala[6, 15](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(EscalaTr((CircleFigure(Position(0, 5), 10)), 6,15), ""))
  }

  it should "parsear correctamente una rotacion con un componente" in {
    rotacion("rotacion[6](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(RotacionTr((CircleFigure(Position(0, 5), 10)),6), ""))
  }

  it should "parsear correctamente una traslación con un componente" in {
    traslacion("traslacion[6, 15](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(TraslacionTr((CircleFigure(Position(0, 5), 10)),6,15), ""))
  }

  it should "parsear correctamente una color con un componente" in {
    color("color[6, 6, 6](\n\tcirculo[0 @ 5, 10]\n)").get shouldEqual (ResultadoParseo(ColorTr((CircleFigure(Position(0, 5), 10)),6,6,6), ""))
  }

  it should "parsear correctamente una cadena larga anidada" in {
    group("grupo(\n    grupo(\n   \t triangulo[250 @ 150, 150 @ 300, 350 @ 300],\n   \t triangulo[150 @ 300, 50 @ 450, 250 @ 450],\n   \t triangulo[350 @ 300, 250 @ 450, 450 @ 450]\n    ),\n    grupo(\n   \t rectangulo[460 @ 90, 470 @ 100],\n   \t rectangulo[430 @ 210, 500 @ 220],\n   \t rectangulo[430 @ 210, 440 @ 230],\n   \t rectangulo[490 @ 210, 500 @ 230],\n   \t rectangulo[450 @ 100, 480 @ 260]\n    )\n)").isSuccess shouldEqual true
  }

  it should "parsear correctamente una cadena larga anidada2" in {
    group("grupo(\n   \t triangulo[250 @ 150, 150 @ 300, 350 @ 300],\n   \t triangulo[150 @ 300, 50 @ 450, 250 @ 450],\n   \t triangulo[350 @ 300, 250 @ 450, 450 @ 450]\n    )").isSuccess shouldEqual true
  }

  it should "parsear correctamente una cadena larga anidada3" in {
    group("grupo(\n\tcolor[0, 0, 80](\n\t\tgrupo(\n\t\t\ttriangulo[50 @ 400, 250 @ 400, 200 @ 420],\n\t\t\ttriangulo[50 @ 440, 250 @ 440, 200 @ 420]\n\t\t)\n\t),\n\tcolor[150, 150, 150](\n\t\ttriangulo[200 @ 420, 250 @ 400, 250 @ 440]\n\t),\n\tcolor[180, 180, 160](\n\t\ttriangulo[330 @ 460, 250 @ 400, 250 @ 440]\n\t),\n\tcolor[200, 200, 180](\n\t\tgrupo(\n\t\t\ttriangulo[330 @ 460, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[330 @ 460, 400 @ 400, 370 @ 450],\n\t\t\ttriangulo[400 @ 430, 400 @ 400, 370 @ 450],\n\t\t\ttriangulo[330 @ 460, 250 @ 400, 330 @ 370]\n\t\t)\n\t),\n\tgrupo(\n\t\tcolor[150, 0, 0](\n\t\t\tgrupo(\n\t\t\t\ttriangulo[430 @ 420, 400 @ 400, 450 @ 400],\n\t\t\t\ttriangulo[430 @ 420, 400 @ 400, 400 @ 430]\n\t\t\t)\n\t\t),\n\t\tcolor[100, 0, 0](triangulo[420 @ 420, 420 @ 400, 400 @ 430]),\n\t\tcolor[0, 0, 60](\n\t\t\tgrupo(\n\t\t\t\ttriangulo[420 @ 400, 400 @ 400, 400 @ 430],\n\t\t\t\ttriangulo[420 @ 380, 400 @ 400, 450 @ 400],\n\t\t\t\ttriangulo[420 @ 380, 400 @ 400, 300 @ 350]\n\t\t\t)\n\t\t),\n\t\tcolor[150, 150, 0](triangulo[440 @ 410, 440 @ 400, 460 @ 400])\n\t),\n\tcolor[0, 0, 60](\n\t\tgrupo(\n\t\t\ttriangulo[330 @ 300, 250 @ 400, 330 @ 370],\n\t\t\ttriangulo[330 @ 300, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[360 @ 280, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[270 @ 240, 100 @ 220, 330 @ 370],\n\t\t\ttriangulo[270 @ 240, 360 @ 280, 330 @ 370]\n\t\t)\n\t)\n)").isSuccess shouldEqual true
  }
}