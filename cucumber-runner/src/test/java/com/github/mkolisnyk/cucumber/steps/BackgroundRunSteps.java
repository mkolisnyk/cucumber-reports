package com.github.mkolisnyk.cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class BackgroundRunSteps {
    @Given("^I activate app with a valid code$")
    public void i_activate_app_with_a_valid_code() throws Throwable {
    }

    @Then("^I am on home screen and in disconnected state$")
    public void i_am_on_home_screen_and_in_disconnected_state() throws Throwable {
        throw new Error();
    }

    @Then("^I disabled internet connection$")
    public void i_disabled_internet_connection() throws Throwable {
    }
}
