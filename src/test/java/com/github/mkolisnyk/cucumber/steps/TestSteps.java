package com.github.mkolisnyk.cucumber.steps;

import org.junit.Assert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestSteps {
    private static int retry = 0;
    
    @Given("^I am out of the system$")
    public void i_am_out_of_the_system() throws Throwable {
        System.out.println("[Step] I'm out of the system");
        retry = 0;
    }
    
    @Given("^I am in the system$")
    public void i_am_in_the_system() throws Throwable {
        System.out.println("[Step] I'm in the system");
    }

    @When("^I do nothing$")
    public void i_do_nothing() throws Throwable {
        System.out.println("[Step] I do nothing");
    }

    @Then("^I should see nothing$")
    public void i_should_see_nothing() throws Throwable {
        System.out.println("[Step] I see nothing");
    }

    @When("^I do something$")
    public void i_do_something() throws Throwable {
        System.out.println("[Step] Doing something...");
        Assert.assertTrue(retry++ > 1);
        retry = 0;
        System.out.println("[Step] DONE!!!");
    }

    @When("^I do wrong$")
    public void i_do_wrong() throws Throwable {
        System.out.println("This gonna fail");
        try {
        Assert.assertTrue(false);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @When("^I do some (\\d+) things$")
    public void i_do_some_things(int value) throws Throwable {
        if (value > 1) {
            System.out.println("[Step] Doing some value...");
            Assert.assertTrue(retry++ > value);
            retry = 0;
            System.out.println("[Step] DONE!!!");
        }
    }
    @When("I use the following text:")
    @Then("I should see the following text:")
    public void docstringText(String text) {
        System.out.println(text);
    }
}
