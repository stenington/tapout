desc "Build the test data"
file :test_data => FileList.new('src/test/resources/tap/**/*.t').pathmap("%X.tap")

rule '.tap' => ['.t'] do |t|
  # generates .tap.err too
  system "perl #{t.source} >#{t.name} 2>#{t.name}.err"
end

desc "Clean up generated test data"
task :clean do
  generated_data = FileList.new(
    'src/test/resources/tap/**/*.tap',
    'src/test/resources/tap/**/*.tap.err')
  sh "rm -f #{generated_data}"
end
