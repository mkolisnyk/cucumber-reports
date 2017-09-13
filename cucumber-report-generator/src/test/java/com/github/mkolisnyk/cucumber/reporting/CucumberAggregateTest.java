package com.github.mkolisnyk.cucumber.reporting;

import org.junit.Assert;
import org.junit.Test;

import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;

public class CucumberAggregateTest {

    @Test
    public void testAggregateBackgroundReRun() throws Exception {
        CucumberDetailedResults report = new CucumberDetailedResults();
        CucumberFeatureResult[] sources = report.readFileContent("src/test/resources/127/cucumber-127.json");
        sources = report.aggregateResults(sources, true);
        Assert.assertEquals(2, sources.length);
        Assert.assertEquals(2, sources[1].getElements().length);
        Assert.assertEquals(2, sources[0].getElements().length);
        report.setOutputDirectory("target/aggregate");
        report.setOutputName("cucumber-127");
        report.setSourceFile("src/test/resources/127/cucumber-127.json");
        report.execute(true, new String[] {"pdf"});
    }
}
