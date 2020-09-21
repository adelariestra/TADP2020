require 'pry' # para poder debuggear más cómodos

module BeforeAndAfter
  attr_accessor :procsBefore, :procsAfter

  def before_and_after(procBefore, procAfter)
    inicializarListas
    @procsBefore << procBefore
    @procsAfter << procAfter
  end

  def inicializarListas
    if @procsBefore.nil?
      @procsBefore = []
      @procsAfter = []
    end
  end

  def method_added(nombre_metodo)
    puts "!! DEBUG !! Nuevo metodo agregado:  #{nombre_metodo}" # Debug!

    chequear_actualizacion do # Método utilizado para evitar recursividad infinita
      metodo = instance_method(nombre_metodo)

      # Hace falta guardar en locales porque las utilizaremos dentro del contexto del proc, el cual no es el mismo que el de la instancia
      procsBefore = @procsBefore
      procsAfter = @procsAfter

      # Redefinición del método para agregarle comportamiento
      define_method(nombre_metodo) do |*args, &bloque|
        procsBefore.each{|procs| self.instance_eval &procs} # Ejecutar el proc en el contexto de self (osea de la clase)
        metodo.bind(self).call(*args, &bloque) # Reconectar el unbound method self (osea la clase)
        procsAfter.each{|procs| self.instance_eval &procs}
      end
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

module Contratos
  def self.included(klass) # Cuando el módulo es incluido....
    klass.extend BeforeAndAfter # Extiendo el comportamiento de la clase para que entienda mensajes del contrato
  end
end
