def before_and_after(procBefore, procAfter)
  self.agregarBeforeAndAfter(procBefore,procAfter)
end

class Contrato
  attr_accessor :procsBefore, :procsAfter

  def initialize(&definicionContrato)
    objeto_nuevo = Object.new

    def objeto_nuevo.inicializarListas
      @procsBefore = []
      @procsAfter = []
    end

    def objeto_nuevo.agregarBeforeAndAfter(procBefore,procAfter)
      @procsBefore << procBefore
      @procsAfter << procAfter
    end

    def objeto_nuevo.getBefores
      @procsBefore
    end

    def objeto_nuevo.getAfters
      @procsAfter
    end

    objeto_nuevo.inicializarListas
    objeto_nuevo.instance_eval(&definicionContrato)
    @objetoOriginal = objeto_nuevo
  end

  def method_missing(nombre_mensaje, *args, &bloque) # Para ejecucion de before y afters
  @objetoOriginal.getBefores.each{|metodoBefore| @objetoOriginal.instance_eval &metodoBefore}
  @objetoOriginal.send(nombre_mensaje, *args, &bloque)
  @objetoOriginal.getAfters.each{|metodoAfter| @objetoOriginal.instance_eval &metodoAfter}
  end

  # Todavia no se usa pa nada
  def self.method_added(method_name)
    puts "!!! Adding #{method_name.inspect}"
  end

end

# USO!
contrato = Contrato.new do
  before_and_after( proc{puts "before"} , proc{puts "after"} )

  def mensajeTest
    puts "medio"
  end

  before_and_after( proc{puts "before"} , proc{puts "after"} )

end

contrato.mensajeTest
