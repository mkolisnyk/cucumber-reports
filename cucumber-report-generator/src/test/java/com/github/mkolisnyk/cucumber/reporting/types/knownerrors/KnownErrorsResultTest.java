package com.github.mkolisnyk.cucumber.reporting.types.knownerrors;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;

@RunWith(Parameterized.class)
public class KnownErrorsResultTest {
    private KnownErrorsResult sample;
    private KnownErrorsResult result;
    private KnownErrorsInfo info;
    private int frequency;
    private KnownErrorOrderBy orderBy;
    private int expectedComparison;
    private boolean expectedEquals;

    public KnownErrorsResultTest(final KnownErrorsInfo info, final int frequency,
            final KnownErrorOrderBy orderBy, int compareValue, boolean equalsValue) {
        super();
        this.sample = new KnownErrorsResult();
        this.sample.setInfo(new KnownErrorsInfo(
                        "Title",
                        "Description",
                        DataDimension.allFeatures(),
                        KnownErrorPriority.MEDIUM));
        this.sample.setFrequency(2);
        this.sample.setOrderBy(KnownErrorOrderBy.NAME);

        this.info = info;
        this.frequency = frequency;
        this.orderBy = orderBy;

        this.result = new KnownErrorsResult();
        this.result.setInfo(info);
        this.result.setFrequency(frequency);
        this.result.setOrderBy(orderBy);

        this.expectedComparison = compareValue;
        this.expectedEquals = equalsValue;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(
            new Object[][] {
                {new KnownErrorsInfo(
                        "Title",
                        "Description",
                        DataDimension.allFeatures(),
                        KnownErrorPriority.HIGHEST), 3, KnownErrorOrderBy.NAME, 0, false
                },
                /*{new KnownErrorsInfo(
                        "Title",
                        "Description",
                        DataDimension.allFeatures(),
                        KnownErrorPriority.HIGHEST), 3, KnownErrorOrderBy.PRIORITY, -2, false
                },*/
                {new KnownErrorsInfo(
                        "Title",
                        "Description",
                        DataDimension.allFeatures(),
                        KnownErrorPriority.HIGHEST), 3, KnownErrorOrderBy.FREQUENCY, -1, false
                },
                {
                    new KnownErrorsInfo(
                            "Title",
                            "Description",
                            DataDimension.allFeatures(),
                            KnownErrorPriority.MEDIUM), 2, KnownErrorOrderBy.NAME, 0, true
                }
            });
    }

    @Test
    public void testResultsComparison() {
        Assert.assertEquals(info, result.getInfo());
        Assert.assertEquals(frequency, result.getFrequency());
        Assert.assertEquals(orderBy, result.getOrderBy());
        Assert.assertEquals(expectedComparison, result.compareTo(sample));
        Assert.assertEquals(expectedEquals, sample.equals(result));
        Assert.assertTrue(result.equals(result));
        Assert.assertFalse(result.equals(null));
    }
}
