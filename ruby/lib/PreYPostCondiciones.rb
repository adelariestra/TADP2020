module PreYPostCondiciones
  def pre (&proc_precondicion)
    # convertir condición a que si da false lance la excepcion apropiada
    nueva_invariante = proc do |*args|
      raise(PreconditionsNotMet.new) unless (instance_exec(*args,&proc_precondicion))
    end

    agregar_pre_buffer(nueva_invariante)
  end

  def post (&proc_postcondicion)
    # convertir condición a que si da false lance la excepcion apropiada
    nueva_invariant = proc do |*args|
      raise(PostconditionsNotMet.new) unless (instance_exec(*args, &proc_postcondicion))
    end
    agregar_post_buffer(nueva_invariant)
  end
end

class PreconditionsNotMet < StandardError
end

class PostconditionsNotMet < StandardError
end