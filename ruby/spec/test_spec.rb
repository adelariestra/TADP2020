# no hace falta los require de todos los paquetes porque los hace en el spec helper

describe BeforeAndAfter do

  describe '#BeforeAndAfter' do
    it 'Al ejecutar un mensaje con before_and_after ejecuta procs definidos' do
      class ClaseTest
        include Contratos

        attr_reader :antes, :despues

        before_and_after_each_call(proc { @antes = 1 }, proc { @despues = 1 })

        def mensajeTest
          # test
        end
      end

      subject = ClaseTest.new
      subject.mensajeTest

      expect(subject.antes).to eq(1)
      expect(subject.despues).to eq(1)
    end

    it 'Al tener un before_and_after, el mensaje envidado se ejecuta correctamente' do
      class ClaseTestt
        attr_reader :antes, :medio, :despues
        include Contratos

        before_and_after_each_call(proc { @antes = 1 }, proc { @despues = 1 })

        def mensajeTest
          @medio = 1
        end

      end

      subject = ClaseTestt.new
      subject.mensajeTest

      expect(subject.antes).to eq(1)
      expect(subject.medio).to eq(1)
      expect(subject.despues).to eq(1)
    end

    it 'Al tener dos before_and_after en la misma clase ejecuta procs definidos' do
      class ClaseTesttt
        attr_reader :antes, :medio, :despues
        include Contratos

        before_and_after_each_call(proc { @antes = 1 }, proc { @despues = 1 })

        def mensajeTest
          @medio = 1
        end

        before_and_after_each_call(proc { @antes += 1 }, proc { @despues += 1 })
      end

      subject = ClaseTesttt.new
      subject.mensajeTest

      expect(subject.antes).to eq(2)
      expect(subject.medio).to eq(1)
      expect(subject.despues).to eq(2)
    end

    it 'Podemos definir los attr después del include' do
      class ClaseTestttt

        include Contratos
        attr_reader :antes, :despues

        before_and_after_each_call(proc { @antes = 1 }, proc { @despues = 1 })

        def mensajeTest
          # test
        end
      end

      subject = ClaseTestttt.new
      subject.mensajeTest

      expect(subject.antes).to eq(1)
      #expect(subject.despues).to eq(1)
    end

    it 'Si alguno de estos procs modifica algun valor sobre el objeto deberia funcionar igual' do
      class ClaseTesttttt
        attr_reader :antes, :despues

        def initialize()
          @antes = 0
          @despues = 0
        end

        include Contratos


        before_and_after_each_call(proc { @antes += 1 }, proc { @despues += 1 })

        def mensajeTest
          # test
        end
      end

      subject = ClaseTesttttt.new
      subject.mensajeTest

      expect(subject.antes).to eq(1)
      expect(subject.despues).to eq(1)
    end

  end
end

describe Invariant do
  describe '#InicializoObjeto' do
    before do
      class Guerrero
        include Contratos
        attr_accessor :vida, :danio

        invariant { danio >= 0}
        invariant { vida >= 0 }
        def initialize(cantidad_vida)
          @vida = cantidad_vida
        end

        def arakiri
          @vida = -999
        end

        def arakiri_fallido
          @vida = 1
        end

      end
    end

    it 'Si al crear el objeto la invariant se cumple, no tira error' do
      expect{ Guerrero.new(15) }.not_to raise_exception(InvalidInvariant)
    end

    it 'Si al crear el objeto la invariant no se cumple, tira error' do
      expect { Guerrero.new(-15) }.to raise_error(InvalidInvariant)
    end

    it 'Si al crear el objeto con los attr abajo del include y la invariant se cumple, no tira error' do
      guerrero = Guerrero.new(15)
      expect  { guerrero.arakiri}
    end
  end

  describe '#InvariantEnMetodo' do
    before do
      class Espadachin
        attr_accessor :vida

        include Contratos
        invariant { vida >= 0 }
        invariant { vida < 1000 }

        def initialize(cantidad_vida)
          self.vida = cantidad_vida
        end

        def arakiri
          self.vida = -999
        end

        def arakiriFallido
          self.vida = 1
        end
      end
    end

    it 'Si al ejecutar un método la invariant se cumple, no tira error' do

      subject = Guerrero.new(15)
      subject.arakiri_fallido
    end

    it 'Si al ejecutar un método la invariant no se cumple, tira error' do
      subject = Guerrero.new(15)
      expect { subject.arakiri }.to raise_error(InvalidInvariant)
    end

    it 'Al haber una clase con más de una invariant, ambas se ejecutan' do
      expect { subject = Espadachin.new(1010) }.to raise_error(InvalidInvariant)
    end
  end
end

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

      pre { paciencia > 80 }
      post { violencia < 50 }
      def retar_persona_sin_matarla
        self.violencia += incremento_violencia
      end

      # este método no se ve afectado por ninguna pre/post condición
      def retar_sin_problemas
        self.violencia += incremento_violencia * 40
      end
    end
  end

  context '#Precondiciones' do
    it 'Si el objeto cumple las precondiciones, ejecuta el metodo' do


      subject = Bibliotecario.new(100,0,0)
      subject.retar_persona_sin_matarla
    end

    it 'Si el objeto NO cumple las precondiciones,lanza excepción' do
      subject = Bibliotecario.new(40,0,0)
      expect { subject.retar_persona_sin_matarla }.to raise_error(PreconditionsNotMet)
    end
  end

  context '#Precondiciones' do
    it 'Si el objeto cumple las postcondiciones ejecuta el método' do
      subject = Bibliotecario.new(100,0,0)
      subject.retar_persona_sin_matarla
    end

    it 'Si el objeto NO cumple las postcondiciones,lanza excepción' do
      subject = Bibliotecario.new(100,0,60)
      expect { subject.retar_persona_sin_matarla }.to raise_error(PostconditionsNotMet)
    end
  end

  context '#PrecondicionesYPostcondiciones' do

    it 'Método suelto no es afectado por precondiciones y postcondiciones anteriores' do
      subject = Bibliotecario.new(0,12220,455)
      subject.retar_sin_problemas
    end

    it 'Deberia poder enviar mensajes y no solo acceder a las variables de instancia' do
      subject = Bibliotecario.new(100,0,0)
      subject.retar_persona_sin_matarla
      subject.paciencia = 50

      subject.retar_sin_problemas
      subject.paciencia.should eq(50)
    end
  end
end

