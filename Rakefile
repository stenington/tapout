perl_tests = FileList.new('src/test/resources/tap/**/*.t')
tap_files = perl_tests.pathmap("%X.tap")
task :test => [:compile, :build_test_data]
task :compile
file :build_test_data => FileList.new('src/test/resources/tap/**/example.tap')
rule '.tap' => ['.t'] do |t|
  sh "perl #{t.source} 2>&1 1>#{t.name}"
end
task :clean do
  sh "rm #{tap_files}"
end
