package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._

import scala.util.Try


class OperacionesSpec extends AnyFlatSpec with should.Matchers {

  it should "parsear correctamente con satisfies" in {
    val sat = integer.satisfies((element:Any)=> {true})
    sat("8").get shouldEqual ResultadoParseo(8,"")
  }

  it should "parsear correctamente con condicion que use resultado" in {
    val sat = integer.satisfies((element:Any)=> element == 8)
    sat("8").get shouldEqual ResultadoParseo(8,"")
  }

  it should "fallar satisfies si falla condicion" in {
    val sat = integer.satisfies((element:Any)=> {false})
    sat("8").isFailure shouldEqual(true)
  }

  it should "fallar satisfies si falla condicion que use resultado" in {
    val sat = integer.satisfies((element:Any)=> {element == 9})
    sat("8").isFailure shouldEqual(true)

  }

  it should "fallar satisfies si falla parser inicial" in {
    val sat = integer.satisfies((element:Any)=> {element == 8})
    sat("b8 8").isFailure shouldEqual(true)
  }

  it should "parsear correctamente con opt positivo" in {
    val talVezIn = string("in").opt
    val precedencia = talVezIn <> string("fija")
    precedencia("infija").get shouldEqual ResultadoParseo((Option("in"),"fija"),"")
  }
  it should "parsear correctamente con opt negativo" in {
    val talVezIn = string("in").opt
    val precedencia = talVezIn <> string("fija")
    precedencia("fija").get shouldEqual ResultadoParseo((None,"fija"),"")
  }

  it should "parsear correctamente con 0 matcheos kleene" in {
    val talVezIn = string("in").*
    talVezIn("fija").get shouldEqual ResultadoParseo(List[String](),"fija")
  }

  it should "parsear correctamente con 3 matcheos kleene" in {
    val talVezIn = string("in").*
    talVezIn("ininina").get shouldEqual ResultadoParseo(List[String]("in","in","in"),"a")
  }


  it should "parsear correctamente con 3 matcheos Clausura Positiva" in {
    val talVezIn = string("in").+
    talVezIn("ininina").get shouldEqual ResultadoParseo(List[String]("in","in","in"),"a")
  }

  it should "fallar con 0 matcheos Clausura Positiva" in {
    val talVezIn = string("in").+
    talVezIn("fija").isFailure shouldEqual true
  }

  it should "parsear correctamente con funcion transformacion Map" in {
    val intCool = integer.map((element) => {element.toString()})
    intCool("88").get shouldEqual ResultadoParseo("88","")
  }

  it should "fallar si no matchea parser map" in {
    val intCool = integer.map((element) => {element.toString()})
    intCool("a88").isFailure shouldEqual true
  }

}