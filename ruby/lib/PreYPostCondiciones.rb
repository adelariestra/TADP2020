module PreYPostCondiciones
  def pre (&proc_precondicion)
    # convertir condición a que si da false lance la excepcion apropiada
    nueva_invariante = proc do
      raise(PreconditionsNotMet.new) unless (instance_eval(&proc_precondicion))
    end

    agregarBeforeABufffer(nueva_invariante)
  end

  def post (&proc_postcondicion)
    # convertir condición a que si da false lance la excepcion apropiada
    nueva_invariant = proc do
      raise(PostconditionsNotMet.new) unless (instance_eval(&proc_postcondicion))
    end
    agregarAfterABufffer(nueva_invariant)
  end
end

class PreconditionsNotMet < StandardError
end

class PostconditionsNotMet < StandardError
end