describe PreYPostCondiciones do
  before do
    class Bibliotecario
      include Contratos
      attr_accessor :paciencia, :violencia, :incremento_violencia

      def initialize(cant_paciencia,cant_violencia,cant_incremento_violencia)
        @paciencia = cant_paciencia
        @violencia = cant_violencia
        @incremento_violencia = cant_incremento_violencia
      end

      pre { @paciencia > 80 }
      post { @violencia < 50 }
      def retar_persona_sin_matarla()
        @violencia  += @incremento_violencia
      end

      # este método no se ve afectado por ninguna pre/post condición
      def retar_sin_problemas()
        @violencia  += @incremento_violencia * 40
      end
    end
  end
  describe '#Precondiciones' do
    it 'Si el objeto cumple las precondiciones, ejecuta el metodo' do
      subject = Bibliotecario.new(100,0,0)
      subject.retar_persona_sin_matarla()
    end

    it 'Si el objeto NO cumple las precondiciones,lanza excepción' do
      subject = Bibliotecario.new(40,0,0)
      expect { subject.retar_persona_sin_matarla() }.to raise_error(PreconditionsNotMet)
    end
  end

  describe '#Precondiciones' do

    it 'Si el objeto cumple las postcondiciones ejecuta el método' do
      subject = Bibliotecario.new(100,0,0)
      subject.retar_persona_sin_matarla()
    end

    it 'Si el objeto NO cumple las postcondiciones,lanza excepción' do
      subject = Bibliotecario.new(100,0,60)
      expect { subject.retar_persona_sin_matarla() }.to raise_error(PostconditionsNotMet)
    end
  end

  describe '#PrecondicionesYPostcondiciones' do

    it 'Método suelto no es afectado por precondiciones y postcondiciones anteriores' do
      subject = Bibliotecario.new(0,12220,455)
      subject.retar_sin_problemas()
    end

    it 'Deberia poder enviar mensajes y no solo acceder a las variables de instancia' do
      class Bibliotecarioo
        include Contratos
        attr_accessor :paciencia, :violencia, :incremento_violencia

        def initialize(cant_paciencia,cant_violencia,cant_incremento_violencia)
          @paciencia = cant_paciencia
          @violencia = cant_violencia
          @incremento_violencia = cant_incremento_violencia
        end

        pre { paciencia > 80 }
        post { @violencia < 50 }
        def retar_persona_sin_matarla()
          @violencia  += @incremento_violencia
        end

        # este método no se ve afectado por ninguna pre/post condición
        def retar_sin_problemas()
          @violencia  += @incremento_violencia * 40
        end
      end

      subject = Bibliotecarioo.new(100,0,0)
      subject.retar_persona_sin_matarla()
    end

    it 'Deberia poder utilizar el nombre de los parámetros de un método en los pre/postcondiciones' do
      class Operaciones
        include Contratos

        #precondición de dividir
        pre { divisor != 0 }
        #postcondición de dividir
        post { |result| result * divisor == dividendo }
        def dividir(dividendo, divisor)
          dividendo / divisor
        end

        # este método no se ve afectado por ninguna pre/post condición
        def restar(minuendo, sustraendo)
          minuendo - sustraendo
        end
      end

      subject = Operaciones.new
      subject.dividir(7,1)
    end
  end
end
