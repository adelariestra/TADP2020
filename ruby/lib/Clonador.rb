class Clonador
  def evaluar_en_clon(instancia_original, procs_a_evaluar)
    if(procs_a_evaluar.is_a? Array)
      procs_a_evaluar.each do |proc_a_evaluar|
        clon = crear_clon(instancia_original)
        clon.instance_eval &proc_a_evaluar unless proc_a_evaluar.nil?
      end
    else
      clon = crear_clon(instancia_original)
      clon.instance_eval &procs_a_evaluar unless procs_a_evaluar.nil?
    end
  end

  def crear_clon(instancia_original)
    clon = instancia_original.clone
    clon.instance_variable_set(:@is_clone, true)
    clon
  end
end
