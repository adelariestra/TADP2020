package simplificators

import elements._
import simplificators._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo

class SimpleElementsSpec extends AnyFlatSpec with should.Matchers {
  //TODO: add more complex and nested tests

  it should "simplificar correctamente nested colors" in {
    figure("color[200, 200, 200](\n   color[6, 6, 6](circulo[0 @ 5, 10])\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual ColorTr((CircleFigure(Position(0, 5), 10)), 6, 6, 6)
  }

  it should "simplificar correctamente transformaciones comunes entre hijos color" in {
    figure("grupo(\n\tcolor[200, 200, 200](rectangulo[100 @ 100, 200 @ 200]),\n\tcolor[200, 200, 200](circulo[100 @ 300, 150])\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("color[200, 200, 200](\n   grupo(\n\trectangulo[100 @ 100, 200 @ 200],\n\tcirculo[100 @ 300, 150]\n   )\n)").get.elementoParseado
  }

  it should "simplificar correctamente transformaciones entre hijos rotation" in {
    figure("grupo(\n\trotacion[200](rectangulo[100 @ 100, 200 @ 200]),\n\trotacion[200](circulo[100 @ 300, 150])\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("rotacion[200](\n   grupo(\n\trectangulo[100 @ 100, 200 @ 200],\n\tcirculo[100 @ 300, 150]\n   )\n)").get.elementoParseado
  }

  it should "no simplificar transformaciones si valor no es comun entre hijos color" in {
    figure("grupo(\n\tcolor[215, 200, 200](rectangulo[100 @ 100, 200 @ 200]),\n\tcolor[200, 200, 200](circulo[100 @ 300, 150])\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("grupo(\n\tcolor[215, 200, 200](rectangulo[100 @ 100, 200 @ 200]),\n\tcolor[200, 200, 200](circulo[100 @ 300, 150])\n)").get.elementoParseado
  }

}
