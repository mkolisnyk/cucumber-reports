package com.github.mkolisnyk.cucumber.types.usage;

import org.junit.Assert;
import org.junit.Test;

import com.cedarsoftware.util.io.JsonObject;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberAggregatedDuration;

public class CucumberAggregatedDurationTest {

    @Test
    public void testInitializeDuration() {
        CucumberAggregatedDuration duration = new CucumberAggregatedDuration(0.1, 0.2);
        Assert.assertEquals(0.1, duration.getAverage(), 0.01);
        Assert.assertEquals(0.2, duration.getMedian(), 0.01);
        duration.setAverage(0.2333);
        duration.setMedian(0.34444);
        Assert.assertEquals(0.2333, duration.getAverage(), 0.00001);
        Assert.assertEquals(0.34444, duration.getMedian(), 0.000001);
    }
    @Test
    public void testGetDurationFromJsonWithIntegers() {
        JsonObject<String, Object> json = new JsonObject<>();
        json.put("average", new Long(1));
        json.put("median", new Long(2));
        CucumberAggregatedDuration duration = new CucumberAggregatedDuration(json);
        Assert.assertEquals(1, duration.getAverage(), 0.01);
        Assert.assertEquals(2, duration.getMedian(), 0.01);
        json.put("average", new Double(1));
        json.put("median", new Double(2));
        duration = new CucumberAggregatedDuration(json);
        Assert.assertEquals(1, duration.getAverage(), 0.01);
        Assert.assertEquals(2, duration.getMedian(), 0.01);
    }
}
