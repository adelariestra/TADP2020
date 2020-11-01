package parsers

import com.sun.net.httpserver.Authenticator.Failure

import scala.util
import scala.util.{Failure, Success, Try}

// TODO: renombrar de parser basico a parser
sealed trait ParserBasico {
  def getResultado(cadena :String): Try[ResultadoParseo] ={
    Try(new ResultadoParseo(null, null))
  }

  // --- Combinators ---
  def <|>(parserBasico2: ParserBasico): ParserBasico = ORComb(this, parserBasico2)


  def <>(parserBasico2: ParserBasico): ParserBasico = ConcatComb(this, parserBasico2)


  def <~(parserBasico2: ParserBasico): ParserBasico = LeftComb(this, parserBasico2)


  def ~>(parserBasico2: ParserBasico): ParserBasico = RightComb(this, parserBasico2)


  def sepBy(parserBasico2: ParserBasico) = SepByComb(this, parserBasico2)


  // --- Operaciones ---
  def satisfies(condicion: Any => Boolean): ParserBasico = {
    SatisfiesOp(this, condicion)
  }

  def opt: ParserBasico = {
    OptionalOp(this)
  }

  def * : ParserBasico = {
    KleeneOp(this)
  }

  //TODO: USAR TRY PARA NO ANDAR PREGUNTANDO EL ISSUCCESS?
  // pasar parsear a un elemento de tipo Try[Any] y que tenga comportamiento que ante el success parsee
  // y ante el failure retorne failure
  /*def parsear(cadena: String): Try[Any] = {
    this match {
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
        if (parserBasico1.parsear(cadena).isSuccess) parserBasico1.parsear(cadena)
        else parserBasico2.parsear(cadena)
      }
      case ConcatComb(parserBasico1, parserBasico2) => {

        val primerParseo = parserBasico1.parsear(cadena)

        if (primerParseo.isSuccess) {
          val segundoParseo = parserBasico1 match {
            case CharP(_) => parserBasico2.parsear(cadena.substring(1, cadena.length));
            case StringP(string) => parserBasico2.parsear(cadena.substring(string.length, cadena.length));
            case OptionalOp(StringP(string)) =>
              if (primerParseo.get == "")
                parserBasico2.parsear(cadena.substring(0, cadena.length)); // TODO: Fixear
              else
                parserBasico2.parsear(cadena.substring(string.length, cadena.length)); // TODO: Fixear
          }
          if (segundoParseo.isSuccess)
            Try(primerParseo.get, segundoParseo.get)
          else Try(throw new Exception("Error"));
        } else Try(throw new Exception("Error"));
      }
      case LeftComb(parserBasico1, parserBasico2) => {
        val primerParseo = parserBasico1.parsear(cadena)
        if ((primerParseo.isSuccess) && (parserBasico2.parsear(cadena).isSuccess))
          primerParseo
        else
          Try(throw new Exception("Error"));
      }
      case RightComb(parserBasico1, parserBasico2) => {
        val segundoParseo = parserBasico2.parsear(cadena)
        if ((segundoParseo.isSuccess) && (parserBasico1.parsear(cadena).isSuccess))
          segundoParseo
        else
          Try(throw new Exception("Error"));
      }
      case SepByComb(parserContenido, parserSeparador) => {
        /*TODO: Resolver*/ Try(throw new Exception("No implementation error"));
      }
      case SatisfiesOp(parserBasico: ParserBasico, condicion) => {
        val primerParseo = parserBasico.parsear(cadena)
        if (primerParseo.isFailure || condicion(primerParseo))
          primerParseo
        else Try(throw new Exception("Error"))
      }
      case OptionalOp(parserBasico: ParserBasico) => {
        val primerParseo = parserBasico.parsear(cadena)
        if (primerParseo.isFailure)
          Success("") // TODO: pasar a option
        else
          primerParseo
      }
      case KleeneOp(parserBasico: ParserBasico) => {
        /*TODO: Resolver*/ Try(throw new Exception("No implementation error"));
      }
      //TODO: Agregar default
    }
  }*/

}

case object AnyCharP extends ParserBasico {
  override def getResultado(cadena: String): Try[ResultadoParseo] = {
    Try(cadena).map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case object IntegerP extends ParserBasico {
  override def getResultado(cadena: String): Try [ResultadoParseo] = {
    CharP('-').getResultado(cadena).transform(
      elem =>  getConcatParseado( elem.getTextoRestante(), "", true),
      elem => getConcatParseado( cadena, "", false)
    )
  }

  def getConcatParseado( cadena: String , valorParseado : String , esNegativo: Boolean ): Try[ResultadoParseo] = {
    DigitP.getResultado(cadena).transform(
      elem => Success(
        getConcatParseado (
          elem.textoRestante,
          concatInt( valorParseado , elem.getResultado, esNegativo),
          false ).get
      ),
      _ => Success( ResultadoParseo( valorParseado.toInt , cadena ) )
    )
  }

  def concatInt(valorParseado: String, aConcatenar: Any, esNegativo: Boolean): String = {
    var stringBase : String =  if (esNegativo) "-" else valorParseado
    val valorAConcatenar = aConcatenar

    (stringBase + valorAConcatenar)
  }


}


case object DigitP extends ParserBasico {
  override def getResultado(cadena: String): Try[ResultadoParseo] = {
    Try(cadena).filter(elemento => elemento.head.isDigit)
      .map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case class CharP(charName: Char) extends ParserBasico {
  override def getResultado(cadena: String): Try[ResultadoParseo] = {
    Try(cadena).filter(elemento => elemento.head.equals(charName))
      .map(elemento => ResultadoParseo(elemento.head.toInt, elemento.tail))
  }
}

case class StringP(stringName: String) extends ParserBasico {
  override def getResultado(cadena: String): Try[ResultadoParseo] = {
    Try(cadena).filter(elemento => elemento.take(stringName.length).equals(stringName))
      .map(elemento => ResultadoParseo(elemento.take(stringName.length), elemento.drop(stringName.length)))
  }
}

case object DoubleP extends ParserBasico {

}

case class ORComb(element1: ParserBasico, element2: ParserBasico) extends ParserBasico {
  override def getResultado(cadena :String): Try[ResultadoParseo] = {
    if (element1.getResultado(cadena).isSuccess) element1.getResultado(cadena)
    else element2.getResultado(cadena)
  }
}

case class ConcatComb(element1: ParserBasico, element2: ParserBasico) extends ParserBasico{
  override def getResultado(cadena :String): Try[ResultadoParseo] = {
    val resultado1 = element1.getResultado(cadena)
    val resultado2 = element2.getResultado(resultado1.get.getTextoRestante())
    Try(ResultadoParseo((resultado1.get.getResultado(), resultado2.get.getResultado()), resultado2.get.getTextoRestante()))
  }
}

case class LeftComb(element1: ParserBasico, element2: ParserBasico) extends ParserBasico{
  override def getResultado(cadena :String): Try[ResultadoParseo] = {
    val resultado1 = element1.getResultado(cadena)
    val resultado2 = element2.getResultado(resultado1.get.getTextoRestante())
    Try(ResultadoParseo(resultado1.get.getResultado(), resultado2.get.getTextoRestante()))
  }
}

case class RightComb(element1: ParserBasico, element2: ParserBasico) extends ParserBasico{
  override def getResultado(cadena :String): Try[ResultadoParseo] = {
    val resultado1 = element1.getResultado(cadena)
    element2.getResultado(resultado1.get.getTextoRestante())
  }
}

case class SepByComb(element1: ParserBasico, element2: ParserBasico) extends ParserBasico

case class SatisfiesOp(element: ParserBasico, condicion: Any => Boolean) extends ParserBasico{
  override def getResultado(cadena :String) :Try[ResultadoParseo] ={
    element.getResultado(cadena).filter(elemento => condicion(elemento.getResultado()))
  }

}

case class OptionalOp(parserBasico: ParserBasico) extends ParserBasico{

}

case class KleeneOp(parserBasico: ParserBasico) extends ParserBasico