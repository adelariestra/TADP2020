describe Invariant do
  before do
    class Guerrero
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
        include Contratos

        attr_accessor :vida

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
        include Contratos

        attr_accessor :vida, :danio

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

      subject = Espadachin2.new(1010, 2011)
      subject.arakiri_fallido
      expect(subject.vida).to eq(1)
    end

    it 'Al haber una clase con más de una invariant con distintos métodos y una no cumplirse, debería fallar' do
      expect { subject = Espadachin2.new(1010, -2011) }.to raise_error(InvalidInvariant)
    end

    it 'Si una invariante utiliza un método que tiene efecto, no quiero que me instancia sea modificada' do
      class Gladiador
        include Contratos

        attr_accessor :vida

        invariant {!esta_a_un_golpe}
        def initialize(cantidad_vida)
          @vida = cantidad_vida
        end

        def esta_a_un_golpe
          @vida -= 10
          @vida <= 0
        end
      end

      subject = Gladiador.new(50)
      expect(subject.vida).to eq(50)
    end
  end
end

