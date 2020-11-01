package parsers

//trait ResultadoParseo

case class ResultadoParseo(resultado : Any, textoRestante :String) {
  def getResultado() :Any = {resultado}
  def getTextoRestante() :String = {textoRestante}
}
