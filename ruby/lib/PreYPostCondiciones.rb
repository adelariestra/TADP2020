module PreYPostCondiciones
  def pre (&procPrecondicion)
    # convertir invariant a que si da false explota
    nuevaInvariant = proc do
      if (instance_eval(&procPrecondicion) == false)
        raise PreconditionsNotMet.new
      end
    end

    agregarBeforeABufffer(nuevaInvariant)
  end

  def post (&procPostcondicion)
    # convertir invariant a que si da false explota
    nuevaInvariant = proc do
      if (instance_eval(&procPostcondicion) == false)
        raise PostconditionsNotMet.new
      end
    end

    agregarAfterABufffer(nuevaInvariant)
  end

end

class PreconditionsNotMet < StandardError
end

class PostconditionsNotMet < StandardError
end