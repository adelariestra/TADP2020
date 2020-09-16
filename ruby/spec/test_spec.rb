
describe Prueba do
  let(:prueba) do
    class ClaseTest
      before_and_after( proc{puts "before"} , proc{puts "after"} )

      def mensajeTest
        puts "medio"
      end

      before_and_after( proc{puts "before"} , proc{puts "after"} )

    end
  end

  describe '#BeforeAndAfter' do
    it 'Al ejecutar un mensaje en clase before_and_after ejecuta procs definidos' do

    end
  end
end