package com.github.mkolisnyk.cucumber.runner;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExtendedParallelCucumberGetThreadsCountTest {

    private int intValue;
    private String stringValue;
    private String systemVariable;
    private String systemVariableValue;
    private int expectedCount;
    public ExtendedParallelCucumberGetThreadsCountTest(int intValue,
            String stringValue, String systemVariable,
            String systemVariableValue, int expectedCount) {
        super();
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.systemVariable = systemVariable;
        this.systemVariableValue = systemVariableValue;
        this.expectedCount = expectedCount;
    }
    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {1, "2", "noVal", "noVal", 2},

            {1, "", "noVal", "noVal", 1},
            {3, "someVal", "noVal", "noVal", 3},
            {3, "someVal", "someVal", "2", 2},
            {3, "someVal", "someVal", "noVal", 3},
        });
    }
    @Test
    public void testGetThreadsCount() {
        System.setProperty(systemVariable, systemVariableValue);
        int actualCount = ExtendedParallelCucumber.getThreadsCount(intValue, stringValue);
        Assert.assertEquals(expectedCount, actualCount);
    }
}
