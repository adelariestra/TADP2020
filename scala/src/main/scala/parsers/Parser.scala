package parsers

import scala.util.Try

// TODO: cambiar nombre de package
package object Parsertest {

  def char (charAMatchear:Char): ParserBasico ={
    CharP(charAMatchear)
  }

  //TODO: hace falta?
  def parsear(cadena: String, parserBasico : ParserBasico): Try[Any] = {
    parserBasico match {
      case AnyCharP =>
        if (!cadena.isEmpty) Try(cadena.head) else Try(throw new Exception("Error")); // TODO: cambiar if !cond
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
      case ORComb(parserBasico1,parserBasico2) =>{
        if(parsear(cadena,parserBasico1).isSuccess)parsear(cadena,parserBasico1)
        else parsear(cadena,parserBasico2)
      }

        //TODO: Agregar default
    }
  }
}

