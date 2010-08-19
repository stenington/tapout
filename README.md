       .-.                                          .-.
       |-|------------------------------------------|-|
       |+|------------------------------------------|+|
       |+|------------------------------------------|+|
       |||    _______           ____        _       |||
        |    |__   __|         / __ \      | |       |
        |       | | __ _ _ __ | |  | |_   _| |_      |
        |       | |/ _` | '_ \| |  | | | | | __|     |
        |       | | (_| | |_) | |__| | |_| | |_      |
        |       |_|\__,_| .__/ \____/ \__,_|\__|     |
        |               | |                          |
       .+.              |_|                         .+.
       |-|------------------------------------------|-|
       |-|------------------------------------------|-|
       |-|------------------------------------------|-|
       |_|                                          |_|


(Thanks, [Text Ascii Art Generator!](http://patorjk.com/software/taag/))

TapOut: TAP-outputting, junit-running goodness
==============================================

## Purpose ##

TapOut does its best to generate TAP (Test Anything Protocol) output
from junit tests.

## Intended usage, maybe ##

    prove --exec 'java -jar tapout.jar' src/test/java/**/*Test.java

where prove is the amazing Perl TAP harness testing utility found 
[here](http://search.cpan.org/~andya/Test-Harness-3.22/bin/prove).

### Current usage example ###

    prove --exec 'env CLASSPATH=test_dep_1:test_dep_2:...:test_dep_n /path/to/tapout/tapout.sh' com.foo.bar.thing.stuff.MyCoolTest [...]

will run `MyCoolTest` and any other tests listed.

    prove --exec 'env CLASSPATH=test_dep_1:test_dep_2:...:test_dep_n /path/to/tapout/tapout.sh' com.foo.bar.thing.stuff.MyCoolTest [...] -Q -m --formatter TAP::Formatter::HTML > test_report.html

will generate a `TAP::Formatter::HTML` report for `MyCoolTest` et al. 
`-m` means merge STDERR and STDOUT, which is nice. `-Q` means 
don't put junk at the top of the report.

And

    env CLASSPATH=test_dep_1:test_dep_2:...:test_dep_n /path/to/tapout/tapout.sh com.foo.bar.thing.stuff.MyCoolTest [...]

is the same as `perl foo.t`; you get the unadulterated TAP output.

Obviously these invocations are still massively long, but they work. Woo! I hope.

## JUnit -> TAP ##

### Perl & Test::More 
A typical Perl test lives in a .t file and looks something like

    ok($condition, $test_name);

or

    is($got, $expected, $test_name);

Any given .t will usually have a bunch of these guys in it. Tests
are run in the order they're encountered in the script.

### JUnit
A typical junit test lives in a java class definition and looks
something like

    @Test
    public void testName(){
      ...
      assertTrue(someCondition);
      assertEquals(expectedValue, gotValue);
    }

And of course any junit test class can also have lots of these.
They are **not** guaranteed to run in any particular order, I 
don't think.

### Correspondence
There's no natural correspondence between junit assertions and anything
in Test::More that I can think of. It's more natural for me to equate 
Test::More tests like `is` or `ok` with junit test methods annotated with
`@Test`. These seem to be the fundamental units of failure.

One trickiness here is that the order of execution in junit isn't 
guaranteed, at least not to my knowledge. I can look into this more,
but it just means I have to get fancier with my own acceptance testing
of junit to TAP translation.

