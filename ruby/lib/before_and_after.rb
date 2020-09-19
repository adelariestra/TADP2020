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

    receptor.define_singleton_method(:method_added) do |nombre_metodo|
      puts "!! DEBUG !! Nuevo metodo agregado:  #{nombre_metodo}" # Debug!

      chequear_actualizacion do

        metodo = instance_method(nombre_metodo)

        define_method(nombre_metodo) do |args, &bloque|
          procsBeforeLocal.each{|procs| self.instance_eval &procs}
          metodo.bind(self).call(args, &bloque)# metodo.bind(self).call() )
          procsAfterLocal.each{|procs| self.instance_eval &procs}
        end

      end

    end
  end

  def chequear_actualizacion
    if(@actualizando == true)
      return
    end
    @actualizando = true

    yield

    @actualizando = false
  end
end

def before_and_after(procBefore, procAfter)
  receptor = procBefore.binding.receiver

  modulitoBAA = BeforeAndAfter
  modulitoBAA.agregarBeforeAndAfter(procBefore, procAfter)
  modulitoBAA.agregarReceptor(receptor)
  receptor.extend modulitoBAA

end

# USO!
claseTest = Class.new do
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