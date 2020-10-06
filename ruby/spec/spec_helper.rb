# Se utiliza para incluir everything lo necesario en los tests.
# Es una relacion fuerte, debe llamarse spec_helper.rb si o si.

require 'rspec' # utilizado para los tests

require_relative '../lib/BeforeAndAfter'
require_relative '../lib/Contratos'
require_relative '../lib/Invariant'
require_relative '../lib/PreYPostCondiciones'
require_relative '../lib/PrePost'
require_relative '../lib/EvaluadorContratos'

