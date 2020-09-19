class Prueba
  def materia
    :tadp
  end
end


class ClaseTest
  include Contract
  attr_reader :antes, :despues
  
  before_and_after( proc{ @antes = 1 } , proc{ @despues = 1 } )

  def mensajeTest
    puts "medio"
  end

  before_and_after( proc{puts "before"} , proc{puts "after"} )

end