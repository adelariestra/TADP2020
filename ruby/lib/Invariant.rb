module Invariant
  def invariant (&procInvariant)
    puts "Inspeccion de la invariante #{procInvariant.inspect}"
    # convertir invariant a que si da false
    nueva_invariante = proc do
      raise InvalidInvariant.new unless instance_eval(&procInvariant)
    end

    # redefinir cualquier metodo existente para que en el after tenga la invariant reconvertida
    # como el método initialize se agrega después de la ejecución, tambien se overridea
    agregar_after(nueva_invariante)
  end
end

class InvalidInvariant < StandardError
end