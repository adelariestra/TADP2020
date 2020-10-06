module BeforeAndAfter
  attr_accessor :procs_before, :procs_after
  attr_accessor :buffer_precondicion, :buffer_postcondicion
  attr_accessor :invariantes

  def self.extended(klass)
    klass.inicializar_listas
  end

  def inicializar_listas
    puts "inicializadas"
    @procs_before ||= []
    @procs_after ||= []
    @invariantes ||= []
    @buffer_pre_post ||= PrePost.new(nil, nil)
  end

  def before_and_after_each_call(proc_before, proc_after)
    @procs_before << proc_before
    @procs_after << proc_after
  end

  def agregar_pre_buffer(proc_before)
    @buffer_pre_post.pre = proc_before
  end

  def agregar_post_buffer(proc_after)
    @buffer_pre_post.post = proc_after
  end

  def agregar_invariante(invariante)
    @invariantes << invariante
  end

  def all_invariants
    if @invariantes.nil?
      []
    else
      @invariantes
    end
  end

  def method_added(nombre_metodo)
    #puts "!! DEBUG !! Nuevo metodo agregado:  #{nombre_metodo}"
    #puts self.instance_variables

    mutex_subrescritura do
      # Método utilizado para evitar recursividad infinita
      metodo = instance_method(nombre_metodo)

      # Hace falta guardar en locales porque las utilizaremos dentro del contexto del proc, el cual no es el mismo que el de la instancia
      procs_before = @procs_before
      procs_after = @procs_after
      pre = @buffer_pre_post.pre
      post = @buffer_pre_post.post
      invariantes = @invariantes
      clonador = Clonador.new
      #parametros = metodo.parameters.map { |parametro| parametro[1] }

      # Redefinición del método para agregarle comportamiento
      define_method(nombre_metodo) do |*args, &bloque|
        puts "Analizo rec METODO: #{nombre_metodo} --------------------------------------"
        @is_clone ||= false
        #puts "ZIP: #{parametros.zip(args)}"

        if (@is_clone)
          procs_before.each { |procs| self.instance_eval &procs }
          resultado = metodo.bind(self).call(*args, &bloque)
          procs_after.each { |procs| self.instance_eval &procs }
        else

          # Precondiciones
          clonador.evaluar_en_clon(self,pre)

          # Agregar metodos del before and after
          procs_before.each { |procs| self.instance_eval &procs } # Ejecutar el proc en el contexto de self (osea de la clase)
          resultado = metodo.bind(self).call(*args, &bloque) # Reconectar el unbound method self (osea la instancia)..
          procs_after.each { |procs| self.instance_eval &procs }

          # Invariantes
          clonador.evaluar_en_clon(self,invariantes) #Se separa de los after por si alguno se ejecuta después de la invariante y modifica al objeto (pudiendo no cumplir la condición de la invariante)

          # Postcondiciones
          clonador.evaluar_en_clon(self,post)
        end
        resultado #Lo guardamos para los métodos que retornan valores
      end
      @buffer_pre_post.limpiar # Para que no afecte a los siguientes métodos
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