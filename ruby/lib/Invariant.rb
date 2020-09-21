module Invariant
  attr_accessor :invariants
  # Invariant tiene forma de condicion, es un proc
  # Esa condición debe analizarse al instanciar un objeto (deaspues del new)
  # y al correr un metodo (after)7
  # Puede definirse mas de una (lsita)
  # si no se cumple, se lanza un excepción

  def invariant (&procInvariant)
    # convertir invariant a que si da false
    nuevaInvariant = proc do
      if (instance_eval(&procInvariant) == false)
        raise InvalidInvariant.new
      end
    end

    # redefinir cualquier metodo existente para que en el after tenga la invariant reconvertida
    agregar_after(nuevaInvariant)

    # redefinir el new para que despues tenga la invariant reconvertida
  end

end

class InvalidInvariant < StandardError
end