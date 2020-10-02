class PrePost
  attr_accessor :pre, :post
  def initialize(proc_before,proc_after)
    puts "ME INICIALIZE"
    @post = proc_after
    @pre = proc_before
  end

  def limpiar
    @post = nil
    @pre = nil
  end

end
