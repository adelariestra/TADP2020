package parsers

import scala.util.{Failure, Success, Try}

trait Parser[T] extends (String => Try[ResultadoParseo[T]]) {
  // combinator
  def <>[U](parser2: Parser[U]) = ConcatComb[T, U](this, parser2)

  def <|>[U](parser2: Parser[U]) = ORComb[T, U](this, parser2)

  def <~[U](parser2: Parser[U]) = LeftComb[T, U](this, parser2)

  def ~>[U](parser2: Parser[U]) = RightComb[T, U](this, parser2)

  def sepBy[U](parser2: Parser[U]) = SepComb[T, U](this, parser2)

  // operators
  def satisfies(funcion: T => Boolean) = SatisfiesOp(this, funcion)

  def opt = OptOp(this)

  def * = KleeneOp(this)

  def + = ClauPoseOp(this)

  def map[U](funcion: T => U) = MapOp[T, U](this, funcion)
}

case object anyChar extends Parser[Char] {
  override def apply(cadena: String) = {
    Try(cadena).map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case class char(charAMatchear: Char) extends Parser[Char] {
  override def apply(cadena: String) = {
    Try(cadena).filter(elemento => elemento.head.equals(charAMatchear))
      .map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case object digit extends Parser[Char] {
  override def apply(cadena: String) = {
    Try(cadena).filter(elemento => elemento.head.isDigit)
      .map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case class string(stringName: String) extends Parser[String] {
  override def apply(cadena: String) = {
    Try(cadena).filter(elemento => elemento.take(stringName.length).equals(stringName))
      .map(elemento => ResultadoParseo(elemento.take(stringName.length), elemento.drop(stringName.length)))
  }
}

case object integer extends Parser[Int] {
  override def apply(cadena: String): Try[ResultadoParseo[Int]] = {
    val parserInteger = char('-').opt <> digit.+
    parserInteger.apply(cadena).map((resultado: ResultadoParseo[Tuple2[Option[Char], List[Char]]]) => {
      val menos = resultado.elementoParseado._1
      var strigMenos = ""
      if (menos.isDefined) {
        strigMenos = menos.get.toString
      } else {
        strigMenos = "";
      }
      val stringCompleto = strigMenos.concat(resultado.elementoParseado._2.mkString);
      ResultadoParseo(stringCompleto.toInt, resultado.cadenaRestante)
    })
  }
}

// TODO: esta muy acoplado, pasar respons de obtener elementos a los case objects/clases?
case object double extends Parser[Double] {
  override def apply(cadena: String) = {
    val decimals = char('.') <> digit.+
    val parserDouble = integer <> decimals.opt
    parserDouble.apply(cadena).map((elem: ResultadoParseo[(Int, Option[(Char, List[Char])])]) => {
      val stringInt = elem.elementoParseado._1.toString
      val decimales = elem.elementoParseado._2
      var stringCompleto =""
      if (decimales.isDefined){
        stringCompleto = stringInt.concat(".").concat(decimales.get._2.mkString)
      }else{
        stringCompleto = stringInt
      }
      ResultadoParseo(stringCompleto.toDouble, elem.cadenaRestante)
    })
  }
}

