VERSION_NUMBER = '0.0'

repositories.remote << 'http://www.ibiblio.org/maven2/'

COMMONS_IO = 'commons-io:commons-io:jar:1.4'
JUNIT = 'junit:junit:jar:4.8.1'

RUNNER = 'tapout.sh'
MAIN = 'com.mikeandcordelia.tapout.TAPRunner'

desc 'Yo.'
define 'tapout' do

  project.version = VERSION_NUMBER

  compile.with JUNIT

  package :jar  

  test.with COMMONS_IO, JUNIT
  test.compile.from(_('src/test/resources/tap')) # compile the example tap-outputters
  test.exclude 'tap.*' # but don't run them
  test.resources :test_data # build test data first!
  
  dotts = FileList.new('src/test/resources/tap/**/*.t')
  task :test_data => dotts.pathmap("%X.tap")
  task :test_data => dotts.pathmap("%X.tap.err")

  rule(/\.tap(\.err)?$/ => ['.t']) do |t|
    # generates .tap.err too
    system "perl #{t.source} >#{t.name} 2>#{t.name}.err"
  end

  # what are the dependencies here?
  task :runner => package(:jar) do
    cp = Buildr.artifacts(JUNIT, package(:jar)).map(&:name).join(File::PATH_SEPARATOR)
    File.open(_(RUNNER), 'w') {|f| f.write("java -cp #{cp}:$CLASSPATH #{MAIN} $*") }
  end

  task :clean do
    rm_rf _(RUNNER)
  end
end
