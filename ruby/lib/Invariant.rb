module Invariant
  def invariant (&procInvariant)
    # convertir invariant a que si da false
    nuevaInvariant = proc do
      if (instance_eval(&procInvariant) == false)
        raise InvalidInvariant.new
      end
    end

    # redefinir cualquier metodo existente para que en el after tenga la invariant reconvertida
    # como el método initialize se agrega después de la ejecución, tambien se overridea
    agregar_after(nuevaInvariant)
  end

end

class InvalidInvariant < StandardError
end