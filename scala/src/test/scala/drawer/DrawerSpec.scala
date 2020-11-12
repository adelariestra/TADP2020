package drawer

import elements._
import simplificators._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo
import tadp.TADPDrawingApp
import tadp.internal.TADPDrawingAdapter

class DrawerSpec extends AnyFlatSpec with should.Matchers {
  //TODO: add more complex and nested tests

//  it should "dibujar correctamente un circulo" in {
//    TADPDrawingApp.drawTree(figure("circulo[100 @ 100, 10]").get.elementoParseado.applyFunction(generalSimplificator))
//  }

  it should "dibujar correctamente un grupo" in {
    TADPDrawingApp.drawTree(figure("grupo(\n   triangulo[200 @ 50, 101 @ 335, 299 @ 335],\n   circulo[0 @ 150, 100]\n)").get.elementoParseado.applyFunction(generalSimplificator))
  }

//  it should "dibujar correctamente un rectangulo" in {
//    TADPDrawingApp.drawTree(figure("rectangulo[100 @ 100, 100 @ 100]").get.elementoParseado.applyFunction(generalSimplificator))
//  }

}
