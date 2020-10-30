package parsers

import scala.util.{Success, Try}

// TODO: cambiar nombre de package
package object Parsertest {

  // TODO: Terminar de poner todas las funciones de los parsers basicos
  def char(charAMatchear: Char): ParserBasico = {
    CharP(charAMatchear)
  }

  def integer: ParserBasico = {
    IntegerP
  }

  def string(stringAMatchear: String): ParserBasico = {
    StringP(stringAMatchear)
  }

  //TODO: USAR TRY PARA NO ANDAR PREGUNTANDO EL ISSUCCESS?
  // pasar parsear a un elemento de tipo Try[Any] y que tenga comportamiento que ante el success parsee
  // y ante el failure retorne failure
  def parsear(cadena: String, parserBasico: ParserBasico): Try[Any] = {
    parserBasico match {
      case AnyCharP =>
        if (!cadena.isEmpty) Try(cadena.head) else Try(throw new Exception("Error")); // TODO: cambiar if !cond
      case DigitP =>
        if (cadena.head.isDigit) Try(cadena.head) else Try(throw new Exception("Error"));
      case CharP(charName: Char) =>
        if (cadena.head == charName) Try(cadena.head) else Try(throw new Exception("Error"));
      case StringP(stringName: String) =>
        if (cadena.slice(0, stringName.length) == stringName) Try(stringName) else Try(throw new Exception("Error"));
      case DoubleP =>
        if (cadena.toDoubleOption.isDefined) Try(cadena.toDouble) else Try(throw new Exception("Error"));
      case IntegerP =>
        if (cadena.toIntOption.isDefined) Try(cadena.toInt) else Try(throw new Exception("Error"));
      case ORComb(parserBasico1, parserBasico2) => {
        if (parsear(cadena, parserBasico1).isSuccess) parsear(cadena, parserBasico1)
        else parsear(cadena, parserBasico2)
      }
      case ConcatComb(parserBasico1, parserBasico2) => {
        val primerParseo = parsear(cadena, parserBasico1)

        if (primerParseo.isSuccess) {
          val segundoParseo = parserBasico1 match {
            case CharP(_) => parsear(cadena.substring(1, cadena.length), parserBasico2);
            case StringP(string) => parsear(cadena.substring(string.length, cadena.length), parserBasico2);
            case OptionalOp(StringP(string)) =>
              if (primerParseo.get == "")
                parsear(cadena.substring(0, cadena.length), parserBasico2); // TODO: Fixear
              else
                parsear(cadena.substring(string.length, cadena.length), parserBasico2); // TODO: Fixear
          }
          if (segundoParseo.isSuccess)
            Try(primerParseo.get, segundoParseo.get)
          else Try(throw new Exception("Error"));
        } else Try(throw new Exception("Error"));
      }
      case LeftComb(parserBasico1, parserBasico2) => {
        val primerParseo = parsear(cadena, parserBasico1)
        if ((primerParseo.isSuccess) && (parsear(cadena, parserBasico2).isSuccess))
          primerParseo
        else
          Try(throw new Exception("Error"));
      }
      case RightComb(parserBasico1, parserBasico2) => {
        val segundoParseo = parsear(cadena, parserBasico2)
        if ((segundoParseo.isSuccess) && (parsear(cadena, parserBasico1).isSuccess))
          segundoParseo
        else
          Try(throw new Exception("Error"));
      }
      case SepByComb(parserContenido, parserSeparador) => {
        /*TODO: Resolver*/ Try(throw new Exception("No implementation error"));
      }
      case SatisfiesOp(parserBasico: ParserBasico, condicion) => {
        val primerParseo = parsear(cadena, parserBasico)
        if (primerParseo.isFailure || condicion(primerParseo))
          primerParseo
        else Try(throw new Exception("Error"))
      }
      case OptionalOp(parserBasico: ParserBasico)=>{
        val primerParseo = parsear(cadena, parserBasico)
        if (primerParseo.isFailure)
          Success("")// TODO: pasar a option
        else
          primerParseo
      }
      //TODO: Agregar default
    }
  }
}

// sepby = contenido <> (separador <> contenido).+
// integer = char('-').opt <> digit.+ =  results.flatmap(...).toInteger
