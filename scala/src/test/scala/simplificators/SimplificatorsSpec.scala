package simplificators

import elements._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class SimplificatorsSpec extends AnyFlatSpec with should.Matchers {
  it should "simplificar correctamente nested colors" in {
    figure("color[200, 200, 200](\n   color[6, 6, 6](circulo[0 @ 5, 10])\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual ColorTr(CircleFigure((0, 5), 10), 6, 6, 6)
  }

  it should "no simplificar un grupo con 2 elementos diferentes" in {
    figure("grupo(\n   rectangulo[200 @ 200, 300 @ 400],\n   rectangulo[200 @ 300, 500 @ 700]\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("grupo(\n   rectangulo[200 @ 200, 300 @ 400],\n   rectangulo[200 @ 300, 500 @ 700]\n)").get.elementoParseado
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

  it should "simplificar correctamente nested rotacion" in {
    figure("rotacion[300](\n\trotacion[10](\n\t\trectangulo[100 @ 200, 300 @ 400]\n\t)\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("rotacion[310](\n\trectangulo[100 @ 200, 300 @ 400]\n)").get.elementoParseado
  }

  it should "simplificar correctamente nested escala" in {
    figure("escala[2, 3](\n      escala[3, 5](\n\t     circulo[0 @ 5, 10]\n      )\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("escala[6, 15](\n\tcirculo[0 @ 5, 10]\n)").get.elementoParseado
  }

  it should "simplificar correctamente nested traslacion" in {
    figure("traslacion[100, 5](\n\ttraslacion[20, 10](\n\t\tcirculo[0 @ 5, 10]\n)\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("traslacion[120, 15](\n\tcirculo[0 @ 5, 10]\n)").get.elementoParseado
  }

  it should "simplificar correctamente null rotacion" in {
    figure("rotacion[0](\n\trectangulo[100 @ 200, 300 @ 400]\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("rectangulo[100 @ 200, 300 @ 400]").get.elementoParseado
  }

  it should "simplificar correctamente null escala" in {
    figure("escala[1, 1](\n\tcirculo[0 @ 5, 10]\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("circulo[0 @ 5, 10]").get.elementoParseado
  }

  it should "simplificar correctamente null traslacion" in {
    figure("traslacion[0, 0](\n\tcirculo[0 @ 5, 10]\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("circulo[0 @ 5, 10]").get.elementoParseado
  }

  it should "simplificar correctamente rotacion con mas de 360 grados" in {
    figure("rotacion[361](\n\trectangulo[100 @ 200, 300 @ 400]\n)").get.elementoParseado.applyFunction(generalSimplificator) shouldEqual figure("rotacion[1](\n\trectangulo[100 @ 200, 300 @ 400]\n)").get.elementoParseado
  }


}
