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


  def parsear(cadena: String, parserBasico: ParserBasico): Try[Any] = {
    parserBasico match {
      case AnyCharP =>
        if (cadena.head.isLetter) Try(cadena.head) else Try(throw new Exception("Error"));
      case DigitP =>
        if (cadena.head.isDigit) Try(cadena.head) else Try(throw new Exception("Error"));
      case CharP(charName: Char) =>
        if (cadena.head == charName) Try(cadena.head) else Try(throw new Exception("Error"));
      case StringP(stringName: String) =>
        if ( cadena.slice(0,stringName.length) == stringName) Try(stringName) else Try(throw new Exception("Error"));
      case DoubleP =>
        if ( cadena.toDoubleOption.isDefined ) Try(cadena.toDouble) else Try(throw new Exception("Error"));
      case IntegerP =>
        if ( cadena.toIntOption.isDefined ) Try(cadena.toInt) else Try(throw new Exception("Error"));

    }
  }
}

