package com.github.mkolisnyk.cucumber.runner;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExtendedParallelConvertPluginPathTest {

    private String inputPath;
    private boolean checkFormat;
    private String expected;
    
    @Parameters(name = "{index} - \"{0}\"")
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {"html:target/cucumber-html-report", false, "html:target/0/cucumber-html-report"},
            {"json:target/cucumber-dry.json", false, "json:target/0/cucumber-dry.json"},
            {"pretty:target/cucumber-pretty-dry.txt", false, "pretty:target/0/cucumber-pretty-dry.txt"},
            {"usage:target/cucumber-usage-dry.json", false, "usage:target/0/cucumber-usage-dry.json"},
            {"junit:target/cucumber-results-dry.xml", false, "junit:target/0/cucumber-results-dry.xml"},
            {"com.github.mkolisnyk.cucumber.runner.CustomReporter", true, "com.github.mkolisnyk.cucumber.runner.CustomReporter"},
            {"com.github.mkolisnyk.cucumber.runner.CustomFormatter:test.txt", true, "com.github.mkolisnyk.cucumber.runner.CustomFormatter:0/test.txt"},
        });
    }

    public ExtendedParallelConvertPluginPathTest(String inputPath, boolean checkFormat, String expected) {
        super();
        this.inputPath = inputPath;
        this.checkFormat = checkFormat;
        this.expected = expected;
    }
    @Test
    public void testConvertPluginPath() {
        String actualPath = ExtendedParallelCucumber.convertPluginPaths(new String[] {inputPath}, 0, checkFormat)[0];
        Assert.assertEquals(expected, actualPath);
    }
}
