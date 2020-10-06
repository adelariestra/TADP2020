
describe BeforeAndAfter do

  describe '#BeforeAndAfter' do
    before do
      class ClaseTest
        include Contratos
        attr_reader :antes, :despues, :medio

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
        include Contratos
        attr_reader :antes, :despues, :medio

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
