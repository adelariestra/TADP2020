package parsers

import scala.util.Try
import scala.util.control.NonFatal

case class ResultadoParseo[T] (elementoParseado :T, cadenaRestante :String)
/*object ResultadoParseo {
  def apply[T](r: => (T,String)) :ResultadoParseo[T] = {
    try ParseoExitoso(r._1, r._2) catch {
      case error :Exception => ErrorParseo(r._2,error)
    }
  }
}



sealed abstract class ResultadoParseo[T] extends Product with Serializable {
  def isErrorParseo :Boolean
  def isParseoExitoso :Boolean
  def getCadena :String
  def getResultado :T

  def map(f: String => (T, String)): ResultadoParseo[T]
  def filter(f: String => Boolean): ResultadoParseo[T]
  def flatMap(f: String => ResultadoParseo[T]): ResultadoParseo[T]
}

case class ParseoExitoso[T](resultado :T, cadena :String) extends ResultadoParseo[T] {
  def isErrorParseo = false
  def isParseoExitoso = true
  def getCadena = cadena
  def getResultado = resultado

  override def map(f: String => (T, String)) :ResultadoParseo[T] = ResultadoParseo(f(cadena))
  override def filter(f: String => Boolean) :ResultadoParseo[T] =
    try {
      if (f(cadena)) this else ErrorParseo(cadena, new NoSuchElementException("Predicate does not hold for " + cadena))
    } catch { case error :Exception => ErrorParseo(cadena, error) }

  override def flatMap(f: String => ResultadoParseo[T]): ResultadoParseo[T] =
    try f(cadena) catch { case error :Exception => ErrorParseo(cadena, error) }


}

case class ErrorParseo[T](cadena :String,exception: Exception) extends ResultadoParseo[T] {
  def isErrorParseo = true
  def isParseoExitoso = false
  def getCadena = ""
  def getResultado = throw new Error("NO HAY RESULTADO")

  override def map(f: String => (T,String)): ResultadoParseo[T] = this
  override def filter(f: String => Boolean): ResultadoParseo[T] = this
  override def flatMap(f: String => ResultadoParseo[T]): ResultadoParseo[T] = this.asInstanceOf[ResultadoParseo[T]]
}*/



