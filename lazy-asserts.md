---
title: Lazy Asserts
layout: default
---

# Where is it used?

In most of the cases when we perform some verifications test will fail and stop after first failed verification. This is the way the JUnit, TestNG and many other unit test engines work. But for higher level tests there may be a necessity to make a wide range of complex checks when we may be interested in multiple check-points and we need to see all the errors, not just the first one. Also, some errors are definitely not critical enough to halt entire test run.

For this purpose lazy assertion support was introduced. The major idea is to have similar set of assertions as JUnit/TestNG has but it throws specific exception which has dedicated handling in [ExtendedCucumber](/cucumber-reports/extended-cucumber-runner) runner. If such assertion is thrown, it just marks step as failed and continues execution.

This feature is available since **1.0.10** version.

# Typical use

The use is identical to any other assertions and it may look like:

{% highlight java linenos=table %}
import com.github.mkolisnyk.cucumber.assertions.LazyAssert;

...

    @When("^I do something$")
    public void i_do_a_bit_wrong() throws Throwable {
        LazyAssert.assertTrue(someCondition(), "The expected condition hasn't been met");
    }
{% endhighlight %}

The only difference is that when condition isn't met the **LazyAssertionError** object is thrown.

# Restrictions

Currently, this functionality works only for [ExtendedCucumber](/cucumber-reports/extended-cucumber-runner) runner which runs as a part of JUnit. So, neither TestNG nor CLI runner support this.