package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._

class CombinatorsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente con combinator <|> matcheando con el primero" in {
    val aob = char('a') <|> char('b')
    aob.getResultado("arbol").get.getResultado() shouldEqual ('a': Char)
  }

  it should "parsear correctamente con combinator <|> matcheando con el segundo" in {
    val aob = char('a') <|> char('b')
    aob.getResultado("bol").get.getResultado() shouldEqual ('b': Char)
  }

  it should "parsear correctamente con combinator <>" in {
    val aob = string("hola") <> string("mundo")
    aob.getResultado("holamundo").get.getResultado() shouldEqual("hola", "mundo")
  }

  it should "parsear correctamente con leftmost combinator" in {
    val aob = string("hola") <~ string("mundo")
    aob.getResultado("holamundo").get.getResultado() shouldEqual "hola"
  }

  it should "fallar leftmost combinator si el primero falla" in {
    val aob = string("hola") <~ string("diferenteholamundo")
    aob.getResultado("diferenteholamundo").isFailure shouldEqual true
  }

  it should "fallar leftmost combinator si el segundo falla" in {
    val aob = string("hola") <~ string("holadiferente")
    aob.getResultado("holamundo").isFailure shouldEqual true
  }

  it should "parsear correctamente con rightmost combinator" in {
    val aob = string("hola") ~> string("holamundo")
    aob.getResultado("holamundo").get.getResultado() shouldEqual "holamundo"
  }
  it should "fallar rightmost combinator si el primero falla" in {
    val aob = string("hola") ~> string("diferenteholamundo")
    aob.getResultado("diferenteholamundo").isFailure shouldEqual true
  }

  it should "fallar rightmost combinator si el segundo falla" in {
    val aob = string("hola") ~> string("holadiferente")
    aob.getResultado("holamundo").isFailure shouldEqual true
  }

//  it should "parsear correctamente con sepBy combinator" in {
//    val aob = integer.sepBy(char('-'))
//    aob.parsear("450-450").get shouldEqual "450-450"
//  }
//
//  it should "fallar sepBy combinator" in {
//    val aob = integer.sepBy(char('-'))
//    aob.parsear("450 450").isFailure shouldEqual true
//  }
}