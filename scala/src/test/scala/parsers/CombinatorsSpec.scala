package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._

class CombinatorsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente con combinator <|> matcheando con el primero" in {
    val aob = char('a') <|> char('b')
    parsear("arbol", aob).get shouldEqual ('a': Char)
  }

  it should "parsear correctamente con combinator <|> matcheando con el segundo" in {
    val aob = char('a') <|> char('b')
    parsear("bol", aob).get shouldEqual ('b': Char)
  }

  it should "parsear correctamente con combinator <>" in {
    val aob = string("hola") <> string("mundo")
    parsear("holamundo", aob).get shouldEqual("hola", "mundo")
  }

  it should "parsear correctamente con leftmost combinator" in {
    val aob = string("hola") <~ string("holamundo")
    parsear("holamundo", aob).get shouldEqual "hola"
  }

  it should "fallar leftmost combinator si el primero falla" in {
    val aob = string("hola") <~ string("diferenteholamundo")
    parsear("diferenteholamundo", aob).isFailure shouldEqual true
  }

  it should "fallar leftmost combinator si el segundo falla" in {
    val aob = string("hola") <~ string("holadiferente")
    parsear("holamundo", aob).isFailure shouldEqual true
  }

  it should "parsear correctamente con rightmost combinator" in {
    val aob = string("hola") ~> string("holamundo")
    parsear("holamundo", aob).get shouldEqual "holamundo"
  }
  it should "fallar rightmost combinator si el primero falla" in {
    val aob = string("hola") ~> string("diferenteholamundo")
    parsear("diferenteholamundo", aob).isFailure shouldEqual true
  }

  it should "fallar rightmost combinator si el segundo falla" in {
    val aob = string("hola") ~> string("holadiferente")
    parsear("holamundo", aob).isFailure shouldEqual true
  }

  it should "parsear correctamente con sepBy combinator" in {
    val aob = integer.sepBy(char('-'))
    parsear("450-450", aob).get shouldEqual "450-450"
  }

  it should "fallar sepBy combinator" in {
    val aob = integer.sepBy(char('-'))
    parsear("450 450", aob).isFailure shouldEqual true
  }
}