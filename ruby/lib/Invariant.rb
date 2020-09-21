module Invariant
  attr_accessor :invariants
  # Invariant tiene forma de condicion, es un proc
  # Esa condición debe analizarse al instanciar un objeto (deaspues del new)
  # y al correr un metodo (after)7
  # Puede definirse mas de una (lsita)
  # si no se cumple, se lanza un excepción

  def invariant(procInvariant)
    inicializarListaInvariants
    @invariants<<procInvariant
  end

  def inicializarListaInvariants
    if @invariants.nill?
      @invariants = []
    end
  end

end

class InvalidInvariant < StandardError
end