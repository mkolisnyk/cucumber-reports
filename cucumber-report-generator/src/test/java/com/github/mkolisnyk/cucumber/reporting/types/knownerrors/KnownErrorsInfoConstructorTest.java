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
public class KnownErrorsInfoConstructorTest {
    private KnownErrorsInfo info;
    private String title;
    private String description;
    private DataDimension filter;
    private KnownErrorPriority priority;

    public KnownErrorsInfoConstructorTest(
            KnownErrorsInfo info,
            String title,
            String description,
            DataDimension filter,
            KnownErrorPriority priority) {
        super();
        this.info = info;
        this.title = title;
        this.description = description;
        this.filter = filter;
        this.priority = priority;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(
            new Object[][] {
                {
                    new KnownErrorsInfo(
                        "Title",
                        "Description",
                        DataDimension.allFeatures(),
                        KnownErrorPriority.HIGHEST), "Title", "Description",
                        DataDimension.allFeatures(), KnownErrorPriority.HIGHEST
                },
                {
                    new KnownErrorsInfo(
                        "Title",
                        DataDimension.allFeatures(),
                        KnownErrorPriority.HIGHEST), "Title", "",
                        DataDimension.allFeatures(), KnownErrorPriority.HIGHEST
                },
                {
                    new KnownErrorsInfo(
                        "Title",
                        "Description",
                        DataDimension.allFeatures()), "Title", "Description",
                        DataDimension.allFeatures(), KnownErrorPriority.MEDIUM
                },
                {
                    new KnownErrorsInfo(
                        "Title",
                        DataDimension.allFeatures()), "Title", "",
                        DataDimension.allFeatures(), KnownErrorPriority.MEDIUM
                },
            }
        );
    }
    @Test
    public void testVerifyConstructor() {
        KnownErrorsInfo other = new KnownErrorsInfo(title, description, filter, priority);
        Assert.assertEquals(title, info.getTitle());
        Assert.assertEquals(description, info.getDescription());
        Assert.assertEquals(filter, info.getFilter());
        Assert.assertEquals(priority, info.getPriority());
        Assert.assertEquals(other, info);
        Assert.assertEquals(info, info);
        Assert.assertNotEquals(info, null);
        Assert.assertNotEquals(info, title);
        Assert.assertEquals(other.hashCode(), info.hashCode());
    }
}
