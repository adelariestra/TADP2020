class EvaluadorContratos
  def evaluar_invariantes_en_clon(instancia_original, invariantes)
      invariantes.each do |invariante|
        clon = crear_clon(instancia_original)
        clon.instance_eval &invariante
      end
  end

  def evaluar_precondicion_en_clon(instancia_original, condicion, parametros)
    clon = crear_clon(instancia_original)
    definir_metodos_de_parametros_para_clon(clon, parametros) # definir getters en clon para que puedan ser usados
    clon.instance_eval &condicion unless condicion.nil?
  end

  def evaluar_postcondicion_en_clon(instancia_original, condicion, parametros, resultado)
    clon = crear_clon(instancia_original)
    definir_metodos_de_parametros_para_clon(clon, parametros)
    clon.instance_exec(resultado, &condicion) unless condicion.nil? # exec = eval pero con argumentos
    # si proc no usa el resultado, lo ignora
  end

  def definir_metodos_de_parametros_para_clon(clon, parametros)
    parametros.each do |parametro|
      nombre_parametro, valor_parametro = parametro # parseo de tupla a dos varaibles
      clon.define_singleton_method(nombre_parametro) do # se define el getter para el parámetro
        valor_parametro
      end
    end
  end

  def crear_clon(instancia_original)
    clon = instancia_original.clone
    clon.instance_variable_set(:@is_clone, true)
    clon
  end
end
