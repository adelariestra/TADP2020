module PreYPostCondiciones
  def pre (&procPrecondicion)
    # convertir condición a que si da false lance la excepcion apropiada
    nuevaInvariant = proc do
      raise(PreconditionsNotMet.new) unless (instance_eval(&procPrecondicion))
    end

    agregarBeforeABufffer(nuevaInvariant)
  end

  def post (&procPostcondicion)
    # convertir condición a que si da false lance la excepcion apropiada
    nuevaInvariant = proc do
      raise(PostconditionsNotMet.new) unless (instance_eval(&procPostcondicion))
    end
    agregarAfterABufffer(nuevaInvariant)
  end
end

class PreconditionsNotMet < StandardError
end

class PostconditionsNotMet < StandardError
end