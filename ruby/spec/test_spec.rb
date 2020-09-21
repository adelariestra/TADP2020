# no hace falta los require de todos los paquetes porque los hace en el spec helper

describe BeforeAndAfter do
  let(:guerrero) do
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

      def arakiriFallido
        @vida = 1
      end

    end

    return Guerrero.new
  end

  describe '#BeforeAndAfter' do
    it 'Al ejecutar un mensaje con before_and_after ejecuta procs definidos' do
      class ClaseTest
        attr_reader :antes, :despues
        include Contratos

        before_and_after_each_call(proc{ @antes = 1 } , proc{ @despues = 1 } )

        def mensajeTest
          # test
        end
      end
      subject =  ClaseTest.new
      subject.mensajeTest

      expect(subject.antes).to eq(1)
      expect(subject.despues).to eq(1)
    end

    it 'Al tener un before_and_after, el mensaje envidado se ejecuta correctamente' do
      class ClaseTestt
        attr_reader :antes, :medio, :despues
        include Contratos

        before_and_after_each_call(proc{ @antes = 1 } , proc{ @despues = 1 } )

        def mensajeTest
          @medio = 1
        end

      end
      subject =  ClaseTestt.new
      subject.mensajeTest

      expect(subject.antes).to eq(1)
      expect(subject.medio).to eq(1)
      expect(subject.despues).to eq(1)
    end

    it 'Al tener dos before_and_after en la misma clase ejecuta procs definidos' do
      class ClaseTesttt
        attr_reader :antes, :medio, :despues
        include Contratos

        before_and_after_each_call(proc{ @antes = 1 } , proc{ @despues = 1 } )

        def mensajeTest
          @medio = 1
        end

        before_and_after_each_call(proc{ @antes += 1 } , proc{ @despues += 1 } )
      end
      subject =  ClaseTesttt.new
      subject.mensajeTest

      expect(subject.antes).to eq(2)
      expect(subject.medio).to eq(1)
      expect(subject.despues).to eq(2)
    end

  end

  describe '#Invariants' do
    it 'Si al crear el objeto la invariant se cumple, no tira error' do
      subject = Guerrero.new(15)
    end

    it 'Si al crear el objeto la invariant no se cumple, tira error' do
      expect{subject = Guerrero.new(-15)}.to raise_error(InvalidInvariant)
    end

    it 'Si al ejecutar un método la invariant se cumple, no tira error' do
        subject = Guerrero.new(15)
        subject.arakiriFallido
    end

    it 'Si al ejecutar un método la invariant no se cumple, tira error' do
      subject = Guerrero.new(15)
      expect{subject.arakiri}.to raise_error(InvalidInvariant)
    end

  end
end

