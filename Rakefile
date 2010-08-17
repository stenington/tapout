perl_tests = FileList.new('src/test/resources/tap/**/*.t')
tap_files = perl_tests.pathmap("%X.tap")
tap_err_files = perl_tests.pathmap("%X.tap.err")
test_data = tap_files.add(tap_err_files)

desc "Build the test data"
file :build_test_data => test_data

rule '.tap' => ['.t'] do |t|
  # generates .tap.err too
  system "perl #{t.source} >#{t.name} 2>#{t.name}.err"
end

desc "Clean up generated test data"
task :clean do
  sh "rm -f #{test_data}"
end
