package com.github.mkolisnyk.cucumber.steps;

import org.junit.Assert;
import org.junit.AssumptionViolatedException;

import com.github.mkolisnyk.cucumber.assertions.LazyAssert;

import cucumber.api.Pending;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestSteps {
    private static int retry = 0;
    public static int counter = 0;

    @Pending
    public class CustomPendingError extends Error {
        private static final long serialVersionUID = 1L;
    }
    
    @Before(value={"@lazy"})
    public void beforeHook() {
    }
    @Before(value={"@failed_hook"})
    public void beforeHook2() {
        throw new AssumptionViolatedException("Failed hook");
    }
    @After
    public void afterHook() {
    }
    
    @Given("^I am out of the system$")
    public void i_am_out_of_the_system() throws Throwable {
        System.out.println("" + Thread.currentThread().getId() + "[Step] I'm out of the system");
        retry = 0;
    }
    
    @Given("^I am in the system$")
    public void i_am_in_the_system() throws Throwable {
        System.out.println("" + Thread.currentThread().getId() + "[Step] I'm in the system");
    }

    @When("^I do nothing$")
    public void i_do_nothing() throws Throwable {
        System.out.println("" + Thread.currentThread().getId() + "[Step] I do nothing");
    }

    @Then("^I should see nothing$")
    public void i_should_see_nothing() throws Throwable {
        System.out.println("" + Thread.currentThread().getId() + "[Step] I see nothing");
    }

    @When("^I do something$")
    public void i_do_something() throws Throwable {
        System.out.println("" + Thread.currentThread().getId() + "[Step] Doing something...");
        Assert.assertTrue(retry++ > 1);
        System.out.println("" + Thread.currentThread().getId() + "[Step] DONE!!!");
    }

    @When("^I do wrong$")
    public void i_do_wrong() throws Throwable {
        System.out.println("" + Thread.currentThread().getId() + "This gonna fail");
        try {
        Assert.assertTrue(false);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
    @When("^I do a bit wrong$")
    public void i_do_a_bit_wrong() throws Throwable {
        LazyAssert.fail();
    }
    @When("^I do pending wrong$")
    public void i_do_pending_wrong() throws Throwable {
        //throw new PendingException();
        throw new AssumptionViolatedException("Pending step");
    }
    @When("^I do another pending wrong$")
    public void i_do_another_pending_wrong() throws Throwable {
        throw new CustomPendingError();
    }
    @When("^I do some (\\d+) things$")
    public void i_do_some_things(int value) throws Throwable {
        if (value > 1) {
            System.out.println("" + Thread.currentThread().getId() + "[Step] Doing some value...");
            Assert.assertTrue(retry++ > value);
            retry = 0;
            System.out.println("" + Thread.currentThread().getId() + "[Step] DONE!!!");
        }
    }
    @When("I use the following text:")
    @Then("I should see the following text:")
    public void docstringText(String text) {
        System.out.println(text);
    }
    @Then("^I should see 3 something$")
    public void i_should_see_something() throws Throwable {
    }
    @Then("^I should see ambiguous step$")
    public void i_should_see_amb_step() throws Throwable {
    }
    @Then("^(.*)ambiguous step$")
    public void i_should_see_amb_step2() throws Throwable {
    }
    @Given("^I am keeping the count$")
    public void i_am_keeping_the_count() throws Throwable {
        TestSteps.counter++;
    }
    @When("^I throw \"([^\"]*)\" exception$")
    public void i_throw_exception(String exception) throws Throwable {
        if (exception.equals("Exception1")) {
            throw new PendingException();
        }
        if (exception.equals("Exception2")) {
            throw new AssertionError();
        }
        if (exception.equals("Exception3")) {
            throw new Exception();
        }
    }
}
