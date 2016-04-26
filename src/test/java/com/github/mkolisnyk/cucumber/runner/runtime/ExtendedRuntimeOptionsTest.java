package com.github.mkolisnyk.cucumber.runner.runtime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

@ExtendedCucumberOptions(
        breakdownReport = true,
        breakdownConfig = "sample_config",
        retryCount = 2
)
public class ExtendedRuntimeOptionsTest {
    ExtendedRuntimeOptions option;

    @Before
    public void preSetUp() {
        System.getProperties().remove("cucumber.reports.breakdownReport");
        System.getProperties().remove("cucumber.reports.retryCount");
        System.getProperties().remove("cucumber.reports.breakdownConfig");
    }

    @Test
    public void testOverrideBoolean() throws Exception {
        System.setProperty("cucumber.reports.breakdownReport", "false");
        option = new ExtendedRuntimeOptions(this.getClass().getAnnotation(ExtendedCucumberOptions.class));
        Assert.assertEquals(option.isBreakdownReport(), false);
        Assert.assertEquals(option.getBreakdownConfig(), "sample_config");
        Assert.assertEquals(option.getRetryCount(), 2);
    }
    @Test
    public void testOverrideInteger() throws Exception {
        System.setProperty("cucumber.reports.retryCount", "4");
        option = new ExtendedRuntimeOptions(this.getClass().getAnnotation(ExtendedCucumberOptions.class));
        Assert.assertEquals(option.isBreakdownReport(), true);
        Assert.assertEquals(option.getBreakdownConfig(), "sample_config");
        Assert.assertEquals(option.getRetryCount(), 4);
    }
    @Test
    public void testOverrideString() throws Exception {
        System.setProperty("cucumber.reports.breakdownConfig", "another_config");
        option = new ExtendedRuntimeOptions(this.getClass().getAnnotation(ExtendedCucumberOptions.class));
        Assert.assertEquals(option.isBreakdownReport(), true);
        Assert.assertEquals(option.getBreakdownConfig(), "another_config");
        Assert.assertEquals(option.getRetryCount(), 2);
    }
}
