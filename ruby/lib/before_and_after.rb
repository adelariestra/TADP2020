require 'pry'

module BeforeAndAfter
  attr_accessor :procsBefore, :procsAfter

  def before_and_after(procBefore, procAfter)
    self.inicializarListas
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

    chequear_actualizacion do
      metodo = instance_method(nombre_metodo)
      procsBefore = @procsBefore
      procsAfter = @procsAfter

      define_method(nombre_metodo) do |args, &bloque|
        procsBefore.each{|procs| self.instance_eval &procs}
        metodo.bind(self).call(args, &bloque)# metodo.bind(self).call() )
        procsAfter.each{|procs| self.instance_eval &procs}
      end
    end
  end

  def chequear_actualizacion
    return if Thread.current[:__actualizando__]

    Thread.current[:__actualizando__] = true
    yield if block_given?
    Thread.current[:__actualizando__] = false
  end
end



module Contratos
  def self.included(klass)
    klass.extend BeforeAndAfter
  end
end

# USO!
claseTest = Class.new do
  include Contratos

  before_and_after( proc{puts "before"} , proc{puts "after"} )

  def mensajeTest (unNumero)
    puts "!! DEBUG !!  Este mensaje va al medio"
    puts unNumero*2
  end

  before_and_after( proc{puts "before de nuevo"} , proc{puts "after de nuevo"} )
end

contratoTest = claseTest.new
contratoTest.mensajeTest(4)

# contratoTest.define_singleton_method :metodoAgregado do
#   puts "contenido metodo agregado"
# end

#contratoTest.define_singleton_method :metodoAgregado do
#  puts "Metodo agregado"
#end

#contratoTest.metodoAgregado