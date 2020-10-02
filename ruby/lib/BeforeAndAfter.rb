module BeforeAndAfter
  attr_accessor :procs_before, :procs_after
  attr_accessor :buffer_precondicion, :buffer_postcondicion
  attr_accessor :invariantes

  def before_and_after_each_call(proc_before, proc_after)
    inicializar_listas_baA
    @procs_before << proc_before
    @procs_after << proc_after
  end

  def agregarBeforeABufffer(proc_before)
    @buffer_precondicion = proc_before
  end

  def agregarAfterABufffer(proc_after)
    @buffer_postcondicion = proc_after
  end

  def limpiar_buffers()
    @buffer_precondicion = nil
    @buffer_postcondicion = nil
  end

  def agregar_invariante(invariante)
    @invariantes ||= []
    @invariantes << invariante
  end

  def all_invariants
    if @invariantes.nil?
      []
    else
      @invariantes
    end
  end

  def inicializar_listas_baA
    return unless @procs_before.nil?
    @procs_before = []
    @procs_after = []
  end

  def method_added(nombre_metodo)
    puts "!! DEBUG !! Nuevo metodo agregado:  #{nombre_metodo}"
    puts self.instance_variables
    inicializar_listas_baA #TODO: deberia ser cuando se incluye el modulo(?)

    mutex_subrescritura do
      # Método utilizado para evitar recursividad infinita
      metodo = instance_method(nombre_metodo)

      # Hace falta guardar en locales porque las utilizaremos dentro del contexto del proc, el cual no es el mismo que el de la instancia
      # TODO: podrian guardarlo en un objeto a las precondiciones y postcondiciones
      procs_before = @procs_before
      procs_after = @procs_after
      buffer_precondicion = @buffer_precondicion
      buffer_postcondicion = @buffer_postcondicion
      invariantes = all_invariants

      # Redefinición del método para agregarle comportamiento
      define_method(nombre_metodo) do |*args, &bloque|
        if !@__evitar_recursividad__.nil?
          return metodo.bind(self).call(*args, &bloque)
        end
        @__evitar_recursividad__ = 0

        puts "METODO: #{nombre_metodo} --------------------------------------"
        # Precondiciones
        self.instance_eval(&buffer_precondicion) unless (buffer_precondicion.nil?)

        # Agregar metodos del before and after
        procs_before.each { |procs| self.instance_eval &procs } # Ejecutar el proc en el contexto de self (osea de la clase)
        resultado = metodo.bind(self).call(*args, &bloque) # Reconectar el unbound method self (osea la instancia)..
        procs_after.each { |procs| self.instance_eval &procs }

        # Invariantes
        invariantes.each{|invariante| self.instance_eval &invariante} #Se separa de los after por si alguno se ejecuta después de la invariante y modifica al objeto (pudiendo no cumplir la condición de la invariante)

        # Postcondiciones
        self.instance_eval(&buffer_postcondicion) unless (buffer_postcondicion.nil?)

        @__evitar_recursividad__ = nil
        resultado #Lo guardamos para los métodos que retornan valores
      end
      limpiar_buffers # Para que no afecte a los siguientes métodos
    end
    #end
  end

  def mutex_subrescritura
    # Para evitar tener un atributo con un nombre que podria existir en la clase, utilizo una "variable" del contexto del thread de ejecución
    return if Thread.current[:__actualizando__] # si ya esta overrideando skipeo

    Thread.current[:__actualizando__] = true
    yield if block_given? # ejecutar el bloque entrante
    Thread.current[:__actualizando__] = false
  end
end