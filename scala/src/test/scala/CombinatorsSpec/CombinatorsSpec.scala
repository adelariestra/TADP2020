package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._
import parsers._
import scala.util.Try

class CombinatorsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente con combinator <|> matcheando con el primero" in{
    val aob = char('a') <|> char('b')
    parsear("arbol",aob).get shouldEqual ('a':Char)
  }

  it should "parsear correctamente con combinator <|> matcheando con el segundo" in{
    val aob = char('a') <|> char('b')
    parsear("bol",aob).get shouldEqual ('b':Char)
  }

  it should "parsear correctamente con combinator <>" in {
    val aob = string("hola") <> string("mundo")
    parsear("holamundo", aob).get shouldEqual ("hola","mundo")
  }

}