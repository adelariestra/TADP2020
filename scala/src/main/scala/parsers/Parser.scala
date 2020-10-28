package parsers
import scala.util.Try


package object Parsertest { // significa que es un paquete con muchas cosas
  /*
  *
  Parser
  - Recibe un string
  - Retorna un resultado (Error o Exitoso(elemento parseado junto con la sección del string que no fue consumida))
  * */

//  sealed trait ResultadoParser{
//    def analizar(cadena:String, f:String => ResultadoParser):ResultadoParser
//  }
//  case class Success extends ResultadoParser{
//    def analizar(cadena:String,f:String => ResultadoParser) = {
//      val resultado: ResultadoParser = analizar(f(cadena:String))
//
//    }
//  }
//  case class Failure extends ResultadoParser{
//    def analizar(cadena:String,f:String => ResultadoParser) = this
//  }

  def algo(cadena:String, parserBasico: ParserBasico): Try[String] ={
    parserBasico match{
      case AnyCharP =>  if(cadena.head.isLetter) Try(cadena.head.toString()) else Try(throw new Exception("Error"));
      case IntegerP =>  if(cadena.head.isDigit) Try(cadena.head.toString()) else Try(throw new Exception("Error"));
    }
  }

}

