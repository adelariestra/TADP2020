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

//  it should "dibujar correctamente un grupo" in {
//    TADPDrawingApp.drawTree(figure("grupo(\n   rectangulo[200 @ 200, 300 @ 400],\n   rectangulo[200 @ 300, 500 @ 700]\n)").get.elementoParseado)
//  }
//
//  it should "dibujar correctamente algo con scala" in {
//    TADPDrawingApp.drawTree(figure("escala[2, 1](grupo(\n   rectangulo[200 @ 200, 300 @ 400],\n   rectangulo[200 @ 300, 500 @ 700]\n)\n)").get.elementoParseado)
//  }

  it should "dibujar correctamente composicion con scala" in {
    TADPDrawingApp.drawTree(figure("escala[1.45, 1.45](\n grupo(\n   color[0, 0, 0](\n     rectangulo[0 @ 0, 400 @ 400]\n   ),\n   color[200, 70, 0](\n     rectangulo[0 @ 0, 180 @ 150]\n   ),\n   color[250, 250, 250](\n     grupo(\n       rectangulo[186 @ 0, 400 @ 150],\n       rectangulo[186 @ 159, 400 @ 240],\n       rectangulo[0 @ 159, 180 @ 240],\n       rectangulo[45 @ 248, 180 @ 400],\n       rectangulo[310 @ 248, 400 @ 400],\n       rectangulo[186 @ 385, 305 @ 400]\n    )\n   ),\n   color[30, 50, 130](\n       rectangulo[186 @ 248, 305 @ 380]\n   ),\n   color[250, 230, 0](\n       rectangulo[0 @ 248, 40 @ 400]\n   )\n )\n)").get.elementoParseado)
  }
//
//    it should "dibujar correctamente un rectangulo" in {
//      TADPDrawingApp.drawTree(figure("rectangulo[100 @ 100, 100 @ 100]").get.elementoParseado.applyFunction(generalSimplificator))
//    }

//  it should "dibujar correctamente un rectangulo" in {
//    TADPDrawingApp.drawTree(figure("rectangulo[100 @ 100, 100 @ 100]").get.elementoParseado.applyFunction(generalSimplificator))
//  }

}
