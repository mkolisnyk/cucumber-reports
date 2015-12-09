package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.testng.Assert;

@RunWith(Parameterized.class)
public class DataDimensionDepthTest {

    private DataDimension dimension;
    private int expectedDepth;
    private int expectedWidth;
    public DataDimensionDepthTest(DataDimension dimensionValue, int expectedDepthValue, int expectedWidthValue) {
        super();
        this.dimension = dimensionValue;
        this.expectedDepth = expectedDepthValue;
        this.expectedWidth = expectedWidthValue;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {new DataDimension(DimensionValue.TAG), 1, 1},
                {new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)"), 1, 1},
                {
                    new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
                         new DataDimension[] {}),
                    1, 1
                 },
                 {
                     new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
                         new DataDimension[] {
                             new DataDimension(DimensionValue.TAG)
                         }),
                     2, 1
                  },
                  {
                      new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
                          new DataDimension[] {
                              new DataDimension(DimensionValue.TAG),
                              new DataDimension(DimensionValue.FEATURE, "(.*)Valid(.*)")
                          }),
                      2, 2
                   },
                   {
                       new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
                           new DataDimension[] {
                               new DataDimension(DimensionValue.TAG, "(.*)", new DataDimension[] {}),
                               new DataDimension(DimensionValue.FEATURE, "(.*)Valid(.*)")
                           }),
                       2, 2
                    },
                    {
                        new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
                            new DataDimension[] {
                                new DataDimension(DimensionValue.TAG, "(.*)",
                                    new DataDimension[] {
                                        new DataDimension(DimensionValue.TAG, "@test")
                                    }),
                                new DataDimension(DimensionValue.FEATURE, "(.*)Valid(.*)")
                            }),
                        3, 2
                     },
                     {
                         new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
                             new DataDimension[] {
                                 new DataDimension(DimensionValue.FEATURE, "(.*)Valid(.*)"),
                                 new DataDimension(DimensionValue.TAG, "(.*)",
                                     new DataDimension[] {
                                         new DataDimension(DimensionValue.TAG, "@test")
                                     })
                             }),
                         3, 2
                      },
                     {
                         new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
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
                             }),
                         4, 2
                      },
                      {
                          new DataDimension(DimensionValue.FEATURE, "(.*)Payment(.*)",
                              new DataDimension[] {
                                  DataDimension.allFeatures(),
                                  DataDimension.allScenarios(),
                                  DataDimension.allSteps(),
                                  DataDimension.allTags()
                          }),
                          2, 4
                       },
        });
    }
    @Test
    public void testDimensionDepth() {
        Assert.assertEquals(dimension.depth(), expectedDepth);
    }
    @Test
    public void testDimensionWidth() {
        Assert.assertEquals(dimension.width(), expectedWidth);
    }
    @Test
    public void testExpandShouldReturnArraysWithLengthLessOrEqualToDepth() {
        DataDimension[][] dimensions = dimension.expand();
        for (DataDimension[] line : dimensions) {
            Assert.assertTrue(line.length > 0);
            Assert.assertTrue(line.length <= dimension.depth());
        }
    }
}
