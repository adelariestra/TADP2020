variable = 15
invariant = proc{variable>14}

nuevaInvariant = proc{
  if invariant.call == false
    puts "ola"
  end
}

nuevaInvariant.call
