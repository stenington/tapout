#!/usr/bin/perl

use strict;
use warnings;

use Test::More 'no_plan';

ok(1, "eez ok");
ok(0, "eez not ok");

is(1, 1, "yes it is");
is(1, 2, "no it isnt");

pass("force pass");
fail("force fail");
