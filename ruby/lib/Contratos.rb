
module Contratos
  # Cuando el módulo es incluido....
  def self.included(klass)
    klass.extend BeforeAndAfter # Extiendo el comportamiento de la clase para que entienda mensajes de BandA
    klass.extend Invariant
  end
end