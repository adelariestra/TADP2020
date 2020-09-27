

module BeforeAndAfter
  attr_accessor :procsBefore, :procsAfter
  attr_accessor :bufferPrecondicion, :bufferPostcondicion

  def before_and_after_each_call(procBefore, procAfter)
    inicializarListasBaA
    @procsBefore << procBefore
    @procsAfter << procAfter
  end

  def agregarBeforeABufffer(procBefore)

    @bufferPrecondicion = procBefore
  end

  def agregarAfterABufffer(procAfter)

    @bufferPostcondicion = procAfter
  end

  def limpiarBuffers()
    @bufferPrecondicion = nil
    @bufferPostcondicion = nil
  end

  def agregar_before(procBefore)
    inicializarListasBaA
    @procsBefore << procBefore
  end

  def agregar_after(procAfter)
    inicializarListasBaA
    @procsAfter << procAfter
  end


  def inicializarListasBaA
    if @procsBefore.nil?
      @procsBefore = []
      @procsAfter = []
    end
  end

  def method_added(nombre_metodo)
    # puts "!! DEBUG !! Nuevo metodo agregado:  #{nombre_metodo}"
    inicializarListasBaA
    chequear_actualizacion do # Método utilizado para evitar recursividad infinita
      metodo = instance_method(nombre_metodo)

      # Hace falta guardar en locales porque las utilizaremos dentro del contexto del proc, el cual no es el mismo que el de la instancia
      procsBefore = @procsBefore
      procsAfter = @procsAfter
      bufferPrecondicion = @bufferPrecondicion
      bufferPostcondicion = @bufferPostcondicion

      # Redefinición del método para agregarle comportamiento
      define_method(nombre_metodo) do |*args, &bloque|
        # Precondiciones
        self.instance_eval(&bufferPrecondicion) unless (bufferPrecondicion.nil?)

        # Agregar metodos del before and after
        procsBefore.each{|procs| self.instance_eval &procs} # Ejecutar el proc en el contexto de self (osea de la clase)
        metodo.bind(self).call(*args, &bloque) # Reconectar el unbound method self (osea la clase)
        procsAfter.each{|procs| self.instance_eval &procs}

        # Postcondiciones
        self.instance_eval(&bufferPostcondicion) unless (bufferPostcondicion.nil?)
      end
      limpiarBuffers() # Para que no afecte a los siguientes métodos
    end
  end

  def chequear_actualizacion
    # Para evitar tener un atributo con un nombre que podria existir en la clase, utilizo una "variable" del contexto del thread de ejecución
    return if Thread.current[:__actualizando__] # si ya esta overrideando skipeo

    Thread.current[:__actualizando__] = true
    yield if block_given?   # ejecutar el bloque entrante
    Thread.current[:__actualizando__] = false
  end
end