module BeforeAndAfter
  attr_accessor :procs_before, :procs_after
  attr_accessor :invariantes

  def self.extended(klass)
    klass.inicializar_listas
  end

  def inicializar_listas
    @procs_before ||= []
    @procs_after ||= []
    @invariantes ||= []
    @accessors ||= []
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

  def attr_accessor(*args)
    # Para capturar los accessors definidos en la clase
    args.each { |arg| @accessors << arg }
    super
  end

  def attr_reader(*args)
    # Para capturar los getters definidos en la clase
    args.each { |arg| @accessors << arg }
    super
  end

  def method_added(nombre_metodo)
    #puts "!! DEBUG !! Nuevo metodo agregado:  #{nombre_metodo}"
    #puts self.instance_variables

    mutex_subrescritura do
      # Método utilizado para evitar recursividad infinita
      metodo = instance_method(nombre_metodo) # Obtengo unbound method de la instancia con el nombre especificado

      # Hace falta guardar en locales porque las utilizaremos dentro del contexto del proc, el cual no es el mismo que el de la instancia
      procs_before = @procs_before
      procs_after = @procs_after
      pre = @buffer_pre_post.pre
      post = @buffer_pre_post.post
      invariantes = @invariantes
      accessors = @accessors
      clonador = EvaluadorContratos.new

      nombres_parametros = metodo.parameters.map { |parametro| parametro[1] } # Obtener nombres de parámetros del método y mapearlo para solo quedar con los nombres (ignorar el "obligatorio" de la tupla)

      # Redefinición del método para agregarle comportamiento
      define_method(nombre_metodo) do |*args, &bloque|
        # DEBUG: puts "Analizo rec METODO: #{nombre_metodo} --------------------------------------"

        if(accessors.include? nombre_metodo) # si es un accessor...
          resultado = metodo.bind(self).call(*args, &bloque)
          puts "Era un accessor! #{nombre_metodo}"
          return resultado
        end

        @is_clone ||= false # Si esta nil lo setea en false

        if (@is_clone) # Se ignoran invariantes y prepost.
          procs_before.each { |procs| self.instance_eval &procs }
          resultado = metodo.bind(self).call(*args, &bloque)
          procs_after.each { |procs| self.instance_eval &procs }
        else
          parametros = nombres_parametros.zip(args) # Agrupar los nombres de argumentos con sus valores en una lista de tuplas.
          # Precondiciones
          clonador.evaluar_precondicion_en_clon(self, pre, parametros)
          # se pasan los parametroos para poder ejecutar condiciones que los utilicen
          # se evalua sobre un clon para que las condiciones y befores de las condiciones no afecten al objeto original.

          # Agregar metodos del before and after
          procs_before.each { |procs| self.instance_eval &procs } # Ejecutar el proc en el contexto de self (osea de la instancia)
          resultado = metodo.bind(self).call(*args, &bloque) # Reconectar el unbound method self (osea la instancia)..
          procs_after.each { |procs| self.instance_eval &procs }

          # Invariantes
          clonador.evaluar_invariantes_en_clon(self, invariantes) #Se separa de los after por si alguno se ejecuta después de la invariante y modifica al objeto (pudiendo no cumplir la condición de la invariante)

          # Postcondiciones
          clonador.evaluar_postcondicion_en_clon(self, post, parametros, resultado)
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
    begin
      yield if block_given? # ejecutar el bloque entrante
    rescue StandardError
      Thread.current[:__actualizando__] = false
      raise
    end
    Thread.current[:__actualizando__] = false
  end
end