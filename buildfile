VERSION_NUMBER = '0.0'

repositories.remote << 'http://www.ibiblio.org/maven2/'

COMMONS_IO = 'commons-io:commons-io:jar:1.4'
JUNIT = 'junit:junit:jar:4.8.1'

desc 'Yo.'
define 'tapout' do

  project.version = VERSION_NUMBER

  compile.with JUNIT

  package :jar  

  test.with COMMONS_IO, JUNIT
  test.compile.from(_('src/test/resources/tap'))
  test.exclude 'tap.*'
  # build test data first!
  test.resources :test_data
  
  dotts = FileList.new('src/test/resources/tap/**/*.t')
  task :test_data => dotts.pathmap("%X.tap")
  task :test_data => dotts.pathmap("%X.tap.err")

  rule(/\.tap(\.err)?$/ => ['.t']) do |t|
    # generates .tap.err too
    system "perl #{t.source} >#{t.name} 2>#{t.name}.err"
  end

end
