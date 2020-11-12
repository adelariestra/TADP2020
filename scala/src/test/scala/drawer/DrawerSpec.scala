package drawer

import elements._
import simplificators._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo
import tadp.TADPDrawingApp
import tadp.internal.TADPDrawingAdapter

class DrawerSpec extends AnyFlatSpec with should.Matchers {
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
  //
  //  it should "dibujar correctamente composicion" in {
  //    TADPDrawingApp.drawTree(figure("escala[1.45, 1.45](\n grupo(\n   color[0, 0, 0](\n     rectangulo[0 @ 0, 400 @ 400]\n   ),\n   color[200, 70, 0](\n     rectangulo[0 @ 0, 180 @ 150]\n   ),\n   color[250, 250, 250](\n     grupo(\n       rectangulo[186 @ 0, 400 @ 150],\n       rectangulo[186 @ 159, 400 @ 240],\n       rectangulo[0 @ 159, 180 @ 240],\n       rectangulo[45 @ 248, 180 @ 400],\n       rectangulo[310 @ 248, 400 @ 400],\n       rectangulo[186 @ 385, 305 @ 400]\n    )\n   ),\n   color[30, 50, 130](\n       rectangulo[186 @ 248, 305 @ 380]\n   ),\n   color[250, 230, 0](\n       rectangulo[0 @ 248, 40 @ 400]\n   )\n )\n)").get.elementoParseado)
  //  }
  //
      it should "dibujar correctamente una pepita" in {
        TADPDrawingApp.drawTree(figure("grupo(\n\tcolor[0, 0, 80](\n\t\tgrupo(\n\t\t\ttriangulo[50 @ 400, 250 @ 400, 200 @ 420],\n\t\t\ttriangulo[50 @ 440, 250 @ 440, 200 @ 420]\n\t\t)\n\t),\n\tcolor[150, 150, 150](\n\t\ttriangulo[200 @ 420, 250 @ 400, 250 @ 440]\n\t),\n\tcolor[180, 180, 160](\n\t\ttriangulo[330 @ 460, 250 @ 400, 250 @ 440]\n\t),\n\tcolor[200, 200, 180](\n\t\tgrupo(\n\t\t\ttriangulo[330 @ 460, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[330 @ 460, 400 @ 400, 370 @ 450],\n\t\t\ttriangulo[400 @ 430, 400 @ 400, 370 @ 450],\n\t\t\ttriangulo[330 @ 460, 250 @ 400, 330 @ 370]\n\t\t)\n\t),\n\tgrupo(\n\t\tcolor[150, 0, 0](\n\t\t\tgrupo(\n\t\t\t\ttriangulo[430 @ 420, 400 @ 400, 450 @ 400],\n\t\t\t\ttriangulo[430 @ 420, 400 @ 400, 400 @ 430]\n\t\t\t)\n\t\t),\n\t\tcolor[100, 0, 0](triangulo[420 @ 420, 420 @ 400, 400 @ 430]),\n\t\tcolor[0, 0, 60](\n\t\t\tgrupo(\n\t\t\t\ttriangulo[420 @ 400, 400 @ 400, 400 @ 430],\n\t\t\t\ttriangulo[420 @ 380, 400 @ 400, 450 @ 400],\n\t\t\t\ttriangulo[420 @ 380, 400 @ 400, 300 @ 350]\n\t\t\t)\n\t\t),\n\t\tcolor[150, 150, 0](triangulo[440 @ 410, 440 @ 400, 460 @ 400])\n\t),\n\tcolor[0, 0, 60](\n\t\tgrupo(\n\t\t\ttriangulo[330 @ 300, 250 @ 400, 330 @ 370],\n\t\t\ttriangulo[330 @ 300, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[360 @ 280, 400 @ 400, 330 @ 370],\n\t\t\ttriangulo[270 @ 240, 100 @ 220, 330 @ 370],\n\t\t\ttriangulo[270 @ 240, 360 @ 280, 330 @ 370]\n\t\t)\n\t)\n)").get.elementoParseado.applyFunction(generalSimplificator))
      }

//      it should "dibujar correctamente un carpincho bostero" in {
//        TADPDrawingApp.drawTree(figure(scala.io.Source.fromFile("resources/carpinchoDeBoca").mkString).get.elementoParseado.applyFunction(generalSimplificator))
//      }

  //  it should "dibujar correctamente un rectangulo" in {
  //    TADPDrawingApp.drawTree(figure("rectangulo[100 @ 100, 100 @ 100]").get.elementoParseado.applyFunction(generalSimplificator))
  //  }

}
