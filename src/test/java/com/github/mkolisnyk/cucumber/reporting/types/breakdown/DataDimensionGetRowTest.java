package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import org.junit.Assert;

import org.junit.Test;

public class DataDimensionGetRowTest {
    private DataDimension data = new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
            new DataDimension[] {
                new DataDimension(DimensionValue.TAG, "(.*)",
                    new DataDimension[] {}),
                new DataDimension(DimensionValue.FEATURE, "(.*)Valid(.*)",
                    new DataDimension[] {
                        new DataDimension(DimensionValue.TAG, "(.*)",
                            new DataDimension[] {
                                new DataDimension(DimensionValue.TAG, "@test")
                            })
                })
            });

    @Test
    public void testGetRowZeroLevel() {
        DataDimension[] result = data.getRow(0);
        Assert.assertEquals(1, result.length);
    }
    @Test
    public void testGetRowFirstLevel() {
        DataDimension[] result = data.getRow(1);
        Assert.assertEquals(2, result.length);
    }
    @Test
    public void testGetRowSecondLevel() {
        DataDimension[] result = data.getRow(2);
        Assert.assertEquals(1, result.length);
    }
}
