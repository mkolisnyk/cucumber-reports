package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import java.util.Arrays;
import java.util.Collection;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.testng.Assert;

@RunWith(Parameterized.class)
public class StringConversionUtilsTransformPathTest {
    private String input;
    private String expected;

    private static String convertCurrentDate(String format) {
        return DateTimeFormat.forPattern(format)
                .withDefaultYear(new DateTime().get(DateTimeFieldType.yearOfEra()))
                .print(new DateTime());
    }

    public StringConversionUtilsTransformPathTest(String inputValue, String expectedValue) {
        super();
        this.input = inputValue;
        this.expected = expectedValue;
    }
    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"", ""},
                {"some_string", "some_string"},
                {null, null},
                {"DATE(yyyy-MM-dd)", convertCurrentDate("yyyy-MM-dd")},
                {"SampleDATE(yyyy-MM-dd)with another format DATE(EEE d MMM, yyyy)",
                    "Sample" + convertCurrentDate("yyyy-MM-dd") + "with another format "
                            + convertCurrentDate("EEE d MMM, yyyy")},
                {"${user.dir}", System.getProperty("user.dir")},
                {"${PATH}", System.getenv("PATH")},
        });
    }
    @Test
    public void testVerifyTransformPathFunctionality() {
        String actual = StringConversionUtils.transformPathString(input);
        Assert.assertEquals(actual, expected, "Unexpected string was generated");
    }
}
