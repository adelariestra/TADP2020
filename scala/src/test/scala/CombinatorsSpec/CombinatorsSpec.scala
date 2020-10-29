package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers._

class CombinatorsSpec extends AnyFlatSpec with should.Matchers {
//  it should "parsear correctamente con combinator <|> matcheando con el primero" in{
//    val aob = char('a') <|> char('b')
//    Parsertest.ejecutarParserCombinado("arbol").get shouldEqual 'a':Char
//  }
//
//  it should "parsear correctamente con combinator <|> matcheando con el segundo" in{
//    val aob = char('a') <|> char('b')
//    Parsertest.ejecutarParserCombinado("arbol").get shouldEqual 'a':Char
//  }

  it should "notacion infija" in {
    class Test(val string:String){
      def <|>(string2: Test): Unit ={
        println("ASDASD" + string + string2.string)
      }

    }
    val ola = new Test("test1")
    val ola2 = new Test("test2")

    ola <|> ola2

  }
}