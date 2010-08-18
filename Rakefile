BUILD_DIR = "./build"
LIBS = FileList.new('lib/**/*.jar')

task :compile => FileList.new('src/main/java/**/*.java', 'src/test/java/**/*.java', 'src/test/tap/**/*.java') do |t|
  puts t.class
  puts t.sources
  puts t.prerequisites
end

#rule '.class' => [
#  proc { |tn| tn.sub(/\.java$/, '.class').sub(/^.*?\/com/, '') }
#] do |t|
#  sh "javac #{t.source} -d #{BUILD_DIR}"
#end

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
