package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._
//import parsers.Parsertest._

class CombinatorsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente con combinator <>" in {
    val aob = string("hola") <> string("mundo")
    aob("holamundo").get shouldEqual ResultadoParseo(("hola", "mundo"), "")
  }

  it should "parsear correctamente con combinator <> con distintos tipos" in {
    val aob = integer <> string("mundo")
    aob("71mundo").get shouldEqual ResultadoParseo((71, "mundo"), "")
  }

  it should "el combinator <> debería fallar si alguno de los parsers falla" in {
    val aob = string("mundo") <> char('-')
    aob("4-").isFailure shouldEqual true
  }

  it should "parsear correctamente con combinator <|> matcheando con el primero" in {
    val aob = char('a') <|> char('b')
    aob("arbol").get shouldEqual ResultadoParseo('a', "rbol")
  }

  it should "parsear correctamente con combinator <|> matcheando con el segundo" in {
    val aob = char('a') <|> char('b')
    aob("bol").get shouldEqual ResultadoParseo('b', "ol")
  }

  it should "parsear correctamente con leftmost combinator" in {
    val aob = string("hola") <~ string("mundo")
    aob("holamundo").get shouldEqual ResultadoParseo("hola", "")
  }

  it should "fallar leftmost combinator si el segundo falla" in {
    val aob = string("hola") <~ string("oladiferente")
    aob("holamundo").isFailure shouldEqual true
  }

  it should "fallar leftmost combinator si el primero falla" in {
    val aob = string("hola") <~ string("diferenteholamundo")
    aob("diferenteholamundo").isFailure shouldEqual true
  }

  it should "parsear correctamente con rightmost combinator" in {
    val aob = string("hola") ~> string("mundo")
    aob("holamundopepe").get shouldEqual ResultadoParseo("mundo","pepe")
  }

  it should "fallar rightmost combinator si el primero falla" in {
    val aob = string("hola") ~> string("diferenteholamundo")
    aob("diferenteholamundo").isFailure shouldEqual true
  }

  it should "fallar rightmost combinator si el segundo falla" in {
    val aob = string("hola") ~> string("holadiferente")
    aob("holamundo").isFailure shouldEqual true
  }

  it should "parsear correctamente un elemento con sepBy combinator" in {
    val sepInts = integer.sepBy(char('-'))
    sepInts("450extra").get shouldEqual ResultadoParseo(List[Int](450),"extra")
  }

  it should "parsear correctamente varios elementos con sepBy combinator" in {
    val sepInts = integer.sepBy(char('-'))
    sepInts("450-450extra").get shouldEqual ResultadoParseo(List[Int](450,450),"extra")
  }

  it should "fallar sepBy combinator si no matchea el parser" in {
    val sepInts = string("ola").sepBy(char('-'))
    sepInts("450-450extra").isFailure shouldEqual true
  }

}
