package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._
//import parsers.Parsertest._

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

//  it should "parsear correctamente con opt positivo" in {
//    val talVezIn = string("in").opt
//    val precedencia = talVezIn <> string("fija")
//    precedencia.getResultado("infija").get.getResultado() shouldEqual ("in","fija")
//  }
//  it should "parsear correctamente con opt negativo" in {
//    val talVezIn = string("in").opt
//    val precedencia = talVezIn <> string("fija")
//    precedencia.getResultado("fija").get.getResultado() shouldEqual ("","fija")
//  }

}