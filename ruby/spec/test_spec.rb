require_relative '../lib/prueba'

describe Prueba do
  subject { ClaseTest.new }

  describe '#BeforeAndAfter' do
    it 'Al ejecutar un mensaje en clase before_and_after ejecuta procs definidos' do
      subject.mensajeTest
      expect(subject.antes).to eq(1)
    end
  end
end