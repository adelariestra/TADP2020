# no hace falta los require de todos los paquetes porque los hace en el spec helper

describe BeforeAndAfter do

  describe '#BeforeAndAfter' do
    it 'Al ejecutar un mensaje con before_and_after ejecuta procs definidos' do
      class ClaseTest
        attr_reader :antes, :despues
        include Contratos

        before_and_after( proc{ @antes = 1 } , proc{ @despues = 1 } )

        def mensajeTest
          puts "medio"
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

        before_and_after( proc{ @antes = 1 } , proc{ @despues = 1 } )

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

        before_and_after( proc{ @antes = 1 } , proc{ @despues = 1 } )

        def mensajeTest
          @medio = 1
        end

        before_and_after( proc{ @antes += 1 } , proc{ @despues += 1 } )
      end
      subject =  ClaseTesttt.new
      subject.mensajeTest

      expect(subject.antes).to eq(2)
      expect(subject.medio).to eq(1)
      expect(subject.despues).to eq(2)
    end

  end
end

