# no hace falta los require de todos los paquetes porque los hace en el spec helper

describe BeforeAndAfter do

  describe '#BeforeAndAfter' do
    before do
      class ClaseTest
        attr_reader :antes, :despues, :medio
        include Contratos

        before_and_after_each_call(proc { @antes = 1 }, proc { @despues = 1 })

        def mensaje_test
          @medio = 1
        end
      end
    end
    it 'Al ejecutar un mensaje con before_and_after ejecuta procs definidos' do
      subject = ClaseTest.new
      subject.mensaje_test

      expect(subject.antes).to eq(1)
      expect(subject.despues).to eq(1)
    end

    it 'Al ejecutar un mensaje que llama a otros de self, ejecuta los procs por cada uno' do
      class ClaseTest3
        attr_reader :antes, :despues, :medio
        def initialize
          @antes = 0
          @despues = 0
        end

        include Contratos

        before_and_after_each_call(proc do
          @antes ||= 0
          @antes += 1
        end, proc do
          @despues||= 0
          @despues += 1
        end)

        def mensaje_test
          puts "ejecutando"
        end

        def mensaje_test2
          puts "ejecutando"
        end

        def mensaje_multiple
          mensaje_test
          mensaje_test
        end
      end

      subject = ClaseTest3.new
      subject.mensaje_multiple

      expect(subject.antes).to eq(3)
      expect(subject.despues).to eq(3)
    end

    it 'Al tener un before_and_after, el mensaje envidado se ejecuta correctamente' do
      subject = ClaseTest.new
      subject.mensaje_test

      expect(subject.antes).to eq(1)
      expect(subject.medio).to eq(1)
      expect(subject.despues).to eq(1)
    end

    it 'Al tener dos before_and_after en la misma clase ejecuta procs definidos' do
      ClaseTest.before_and_after_each_call(proc { @antes += 1 }, proc { @despues += 1 })
      subject = ClaseTest.new
      subject.mensaje_test

      expect(subject.antes).to eq(2)
      expect(subject.medio).to eq(1)
      expect(subject.despues).to eq(2)
    end

    it 'Podemos definir los attr después del include' do
      class ClaseTest2

        include Contratos
        attr_reader :antes, :despues

        before_and_after_each_call(proc { @antes = 1 }, proc { @despues = 1 })

        def mensaje_test
          # test
        end
      end

      subject = ClaseTest2.new
      subject.mensaje_test

      expect(subject.antes).to eq(1)
      expect(subject.despues).to eq(1)
    end
  end
end

describe Invariant do
  before do
    class Guerrero

      attr_accessor :vida
      include Contratos

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

  describe '#InicializoObjeto' do

    it 'Si al crear el objeto la invariant se cumple, no tira error' do
      subject = Guerrero.new(15)
    end

    it 'Si al crear el objeto la invariant no se cumple, tira error' do
      expect { subject = Guerrero.new(-15) }.to raise_error(InvalidInvariant)
    end
  end

  it 'Si al crear el objeto con los attr abajo del include y la invariant se cumple, no tira error' do
    class Guerrero2

      include Contratos
      attr_accessor :vida

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

    subject = Guerrero2.new(15)
  end

  describe '#InvariantEnMetodo' do

    it 'Si al ejecutar un método la invariant se cumple, no tira error' do

      subject = Guerrero.new(15)
      subject.arakiri_fallido
    end

    it 'Si al ejecutar un método la invariant no se cumple, tira error' do
      subject = Guerrero.new(15)
      expect { subject.arakiri }.to raise_error(InvalidInvariant)
    end

    it 'Al haber una clase con más de una invariant, ambas se ejecutan' do
      class Espadachin
        attr_accessor :vida

        include Contratos
        invariant { vida >= 0 }
        invariant { vida < 1000 }

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

      expect { subject = Espadachin.new(1010) }.to raise_error(InvalidInvariant)
    end

    it 'Al haber una clase con más de una invariant con distintos métodos, ambas se ejecutan' do
      class Espadachin2
        attr_accessor :vida, :danio

        include Contratos
        invariant { vida >= 0 }
        invariant { danio >= 0 }

        def initialize(cantidad_vida, cantidad_danio)
          @vida = cantidad_vida
          @danio = cantidad_danio
        end

        def arakiri
          @vida = -999
        end

        def arakiri_fallido
          @vida = 1
        end

      end

      expect { subject = Espadachin2.new(1010, 2011) }
    end

    it 'Al haber una clase con más de una invariant con distintos métodos y una no cumplirse, debería fallar' do
      expect { subject = Espadachin2.new(1010, -2011) }.to raise_error(InvalidInvariant)
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
  end
end

