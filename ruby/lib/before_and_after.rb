module BeforeAndAfter
  attr_accessor :procsBefore, :procsAfter

  def self.inicializarListas
    if @procsBefore.nil?
      @procsBefore = []
      @procsAfter = []
    end
  end

  def self.agregarBeforeAndAfter(procBefore,procAfter)
    self.inicializarListas
    @procsBefore << procBefore
    @procsAfter << procAfter
  end

  def self.agregarReceptor(receptor)
    @receptor = receptor
    procsBeforeLocal = @procsBefore
    procsAfterLocal = @procsAfter

    @receptor.define_singleton_method(:method_added) do |nombre_metodo|
      puts "!! DEBUG !! Nuevo metodo agregado:  #{nombre_metodo}" # Debug!

      if(@actualizando == true)
        return
      end
      @actualizando = true

      metodo = instance_method(nombre_metodo)

      define_method(nombre_metodo) do |*args, &bloque|
        procsBeforeLocal.each{|procs| self.instance_eval &procs}
        puts ("!! DEBUG !! Método siendo ejecutado:  #{nombre_metodo}") # metodo.call(*args, &bloque)
        procsAfterLocal.each{|procs| self.instance_eval &procs}
      end

      @actualizando = false
    end
  end
end

def before_and_after(procBefore, procAfter)
  receptor = procBefore.binding.receiver

  modulitoBAA = BeforeAndAfter
  modulitoBAA.agregarBeforeAndAfter(procBefore, procAfter)
  modulitoBAA.agregarReceptor(receptor)
  receptor.include modulitoBAA

end

# USO!
claseTest = Class.new do
  before_and_after( proc{puts "before"} , proc{puts "after"} )

  def mensajeTest
    puts "!! DEBUG !!  Este mensaje va al medio"
  end

  before_and_after( proc{puts "before de nuevo"} , proc{puts "after de nuevo"} )
end

contratoTest = claseTest.new
contratoTest.mensajeTest

# contratoTest.define_singleton_method :metodoAgregado do
#   puts "contenido metodo agregado"
# end

contratoTest.define_singleton_method :metodoAgregado do
  puts "Metodo agregado"
end

contratoTest.metodoAgregado