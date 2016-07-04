package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;

@RunWith(Parameterized.class)
public class CucumberUsageGetFrequencyTest {

    private String json;
    private int expectedSize;
    private int[] expectedFrequencies;

    public CucumberUsageGetFrequencyTest(String jsonValue, int size, int[] frequencies) {
        json = jsonValue;
        expectedSize = size;
        expectedFrequencies = frequencies;
    }

    @Parameters
    public static Collection<Object[]> getParameters() throws IOException {
        return Arrays.asList(new Object[][] {
                {FileUtils.readFileToString(new File("src/test/resources/usage-source/sample1.json")), 0, new int[] {}},
                {FileUtils.readFileToString(new File("src/test/resources/usage-source/sample2.json")), 2, new int[] {5,3}},
                {FileUtils.readFileToString(new File("src/test/resources/usage-source/sample3.json")), 10, new int[] {51,12,3,5,0,0,0,0,0,2}},
                {FileUtils.readFileToString(new File("src/test/resources/usage-source/sample4.json")), 0, new int[] {}},
        });
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFrequencies() throws IOException {
        JsonReader jr = new JsonReader(IOUtils.toInputStream(json, "UTF-8"), true);
        JsonObject<String, Object> sourceObj = (JsonObject<String, Object>) jr.readObject();
        CucumberStepSource source = new CucumberStepSource(sourceObj);
        jr.close();
        CucumberUsageReporting report = new CucumberUsageReporting();
        int[] actualFrequencies = report.getFrequencyData(source);
        Assert.assertEquals("Frequency arrays size is different", expectedSize, actualFrequencies.length);
        Assert.assertArrayEquals("Frequencies are different", expectedFrequencies, actualFrequencies);
    }
}
