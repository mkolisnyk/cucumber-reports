package com.github.mkolisnyk.cucumber.reporting;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.testng.Assert;

@RunWith(Parameterized.class)
public class CucumberDetailedReportGenerateNameFromIdTest {
    private String input;
    private String expected;

    public CucumberDetailedReportGenerateNameFromIdTest(String inputValue, String expectedValue) {
        super();
        this.input = inputValue;
        this.expected = expectedValue;
    }
    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"", ""},
                {"some_string", "some_string"},
                {null, "null"},
                {"some String", "some_String"},
                {"some     String", "some_____String"},
                {"s+o-m<html>e_string", "s_o_m_html_e_string"},
        });
    }
    @Test
    public void testVerifyGenerateNameFromId() {
        String actual = new CucumberDetailedResults().generateNameFromId(input);
        Assert.assertEquals(actual, expected, "Unexpected string was generated");
    }
}
