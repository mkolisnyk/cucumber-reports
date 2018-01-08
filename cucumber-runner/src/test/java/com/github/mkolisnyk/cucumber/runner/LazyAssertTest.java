package com.github.mkolisnyk.cucumber.runner;

import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.assertions.LazyAssert;
import com.github.mkolisnyk.cucumber.assertions.LazyAssertionError;

import cucumber.api.CucumberOptions;
import cucumber.runtime.ExtendedRuntime;

//@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json",
        retryCount = 0,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = false,
        toPDF = false,
        outputFolder = "target/lazy")
@CucumberOptions(
        features = { "./src/test/java/com/github/mkolisnyk/cucumber/features/LazyAssert.feature" },
        glue = { "com/github/mkolisnyk/cucumber/steps" },
        plugin = {
        "json:target/cucumber.json", "html:target/cucumber-html-report",
        "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json",
        "junit:target/cucumber-junit-results.xml" }, tags = {})
public class LazyAssertTest {
    @Test
    public void testRunLazyAsserts() throws Exception {
        ExtendedCucumber runner = new ExtendedCucumber(this.getClass());
        RunNotifier notifier = new RunNotifier();
        runner.run(notifier);
    }
    @Test
    public void testFailedAssertTrueWithMessage() {
        try {
            LazyAssert.assertTrue("Sample message", false);
            Assert.fail("Previous instruction was supposed to fail!");
        } catch (LazyAssertionError e) {
            Assert.assertEquals(e.getMessage(), "Sample message");
        }
    }
    @Test
    public void testFailedAssertTrueWithoutMessage() {
        try {
            LazyAssert.assertTrue(false);
            Assert.fail("Previous instruction was supposed to fail!");
        } catch (LazyAssertionError e) {
            Assert.assertNull(e.getMessage());
        }
    }
    @Test
    public void testPasserAssertTrue() {
        LazyAssert.assertTrue(true);
    }
    @Test
    public void testExtendedRuntimeIsPending() {
        Assert.assertFalse(ExtendedRuntime.isPending(null), "Pending check for null parameter should return false");
    }
    @Test
    public void testAssertTrue() {
        LazyAssert.assertTrue(true);
        try {
            LazyAssert.assertTrue("Some error", false);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertFalse() {
        LazyAssert.assertFalse("Failed step", false);
        try {
            LazyAssert.assertFalse(true);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testFail() {
        try {
            LazyAssert.fail("Some error");
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
        try {
            LazyAssert.fail();
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertEqualsObjectObject() {
        LazyAssert.assertEquals("Sample match", "", "   ".trim());
        LazyAssert.assertEquals(new Integer(5), new Integer(5));
        try {
            LazyAssert.assertEquals("", null);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertNotEqualsStringObjectObject() {
        LazyAssert.assertNotEquals("Sample match", "", "   ");
        LazyAssert.assertNotEquals(new Integer(5), new Integer(4));
        try {
            LazyAssert.assertNotEquals("", "   ".trim());
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertNotEqualsStringLongLong() {
        LazyAssert.assertNotEquals("Not equals", 0L, 1L);
        try {
            LazyAssert.assertNotEquals(5L, 5L);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertNotEqualsStringDoubleDoubleDouble() {
        LazyAssert.assertNotEquals("Not equals", 0.1, 0.102, 0.0001);
        try {
            LazyAssert.assertNotEquals(0.1, 0.10000002, 0.00001);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertNotEqualsFloatFloatFloat() {
        LazyAssert.assertNotEquals("Not equals", 0.1f, 0.1002f, 0.0001f);
        try {
            LazyAssert.assertNotEquals(0.1f, 0.10000002f, 0.00001f);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAssertArrayEqualsStringObjectArrayObjectArray() {
        LazyAssert.assertArrayEquals("Equals", new String[] {"a", "b", "c"}, new String[] {"a", "b", "c"});
        LazyAssert.assertArrayEquals("Equals", new String[] {}, new String[] {});
        try {
            LazyAssert.assertArrayEquals(new String[] {}, null);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
        try {
            LazyAssert.assertArrayEquals(new String[] {"a", "b"}, new Integer[] {1, 2});
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringBooleanArrayBooleanArray() {
        LazyAssert.assertArrayEquals("Equals", new boolean[] {true, false, true}, new boolean[] {true, false, true});
        LazyAssert.assertArrayEquals("Equals", new boolean[] {}, new boolean[] {});
        try {
            LazyAssert.assertArrayEquals(new boolean[] {true, true, true}, new boolean[] {false, true});
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            e.printStackTrace();
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringByteArrayByteArray() {
        LazyAssert.assertArrayEquals("Equals", new byte[] {2, 3, 4}, new byte[] {2, 3, 4});
        LazyAssert.assertArrayEquals("Equals", new byte[] {}, new byte[] {});
        try {
            LazyAssert.assertArrayEquals(new byte[] {2, 3, 4}, new byte[] {3, 4});
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringCharArrayCharArray() {
        LazyAssert.assertArrayEquals("Equals", new char[] {'2', '3', '4'}, new char[] {'2', '3', '4'});
        LazyAssert.assertArrayEquals("Equals", new char[] {}, new char[] {});
        try {
            LazyAssert.assertArrayEquals(new char[] {'2', '3', '4'}, new char[] {'3', '4'});
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringShortArrayShortArray() {
        LazyAssert.assertArrayEquals("Equals", new short[] {2, 3, 4}, new short[] {2, 3, 4});
        LazyAssert.assertArrayEquals("Equals", new short[] {}, new short[] {});
        try {
            LazyAssert.assertArrayEquals(new short[] {2, 3, 4}, new short[] {3, 4});
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringIntArrayIntArray() {
        LazyAssert.assertArrayEquals("Equals", new int[] {2, 3, 4}, new int[] {2, 3, 4});
        LazyAssert.assertArrayEquals("Equals", new int[] {}, new int[] {});
        try {
            LazyAssert.assertEquals(new int[] {2, 3, 4}, new int[] {3, 4});
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringLongArrayLongArray() {
        LazyAssert.assertArrayEquals("Equals", new long[] {2, 3, 4}, new long[] {2, 3, 4});
        LazyAssert.assertArrayEquals("Equals", new long[] {}, new long[] {});
        try {
            LazyAssert.assertArrayEquals(new long[] {2, 3, 4}, new long[] {3, 4});
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringDoubleArrayDoubleArrayDouble() {
        LazyAssert.assertArrayEquals("Equals", new double[] {2, 3, 4}, new double[] {2, 3, 4}, 0.01);
        LazyAssert.assertArrayEquals("Equals", new double[] {}, new double[] {}, 0.01);
        try {
            LazyAssert.assertArrayEquals(new double[] {2, 3, 4}, new double[] {3, 4}, 0.01);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }

    @Test
    public void testAssertArrayEqualsStringFloatArrayFloatArrayFloat() {
        LazyAssert.assertArrayEquals("Equals", new float[] {2, 3, 4}, new float[] {2, 3, 4}, 0.1f);
        LazyAssert.assertArrayEquals("Equals", new float[] {}, new float[] {}, 0.1f);
        try {
            LazyAssert.assertArrayEquals(new float[] {2, 3, 4}, new float[] {3, 4}, 0.1f);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            Assert.fail("Exception wasn't thrown");
        }
    }
/*
    @Test
    public void testAssertEqualsStringDoubleDoubleDouble() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsStringFloatFloatFloat() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertNotEqualsStringFloatFloatFloat() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsLongLong() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsStringLongLong() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsDoubleDouble() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsStringDoubleDouble() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsDoubleDoubleDouble() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsFloatFloatFloat() {
        fail("Not yet implemented"); // TODO
    }
*/
    @Test
    public void testAssertNotNullStringObject() {
        LazyAssert.assertNotNull("Should be not null", "fdfd");
        try {
            LazyAssert.assertNotNull("Should be not null", null);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            LazyAssert.fail("Failure was expected");
        }
        LazyAssert.assertNotNull("fdfd");
        try {
            LazyAssert.assertNotNull(null);
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            LazyAssert.fail("Failure was expected");
        }
    }

    @Test
    public void testAssertNullStringObject() {
        LazyAssert.assertNull("Should be not null", null);
        try {
            LazyAssert.assertNull("Should be not null", "fdfd");
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            LazyAssert.fail("Failure was expected");
        }
        LazyAssert.assertNull(null);
        try {
            LazyAssert.assertNull("fdfd");
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            LazyAssert.fail("Failure was expected");
        }
    }
/*
    @Test
    public void testAssertSameStringObjectObject() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertSameObjectObject() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertNotSameStringObjectObject() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertNotSameObjectObject() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testFormat() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsStringObjectArrayObjectArray() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertEqualsObjectArrayObjectArray() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAssertThatTMatcherOfQsuperT() {
        fail("Not yet implemented"); // TODO
    }
*/
    @Test
    public void testAssertThatStringTMatcherOfQsuperT() {
        LazyAssert.assertThat("", "Some String", is(containsString("Strin")));
        try {
            LazyAssert.assertThat("Some String", is(not(containsString("Strin"))));
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            LazyAssert.fail("Failure was expected");
        }
        LazyAssert.assertThat("Some String", is(containsString("Strin")));
        try {
            LazyAssert.assertThat("", "Some String", is(not(containsString("Strin"))));
            Assert.fail("This step was supposed to fail");
        } catch (LazyAssertionError e) {
        } catch (Throwable e) {
            LazyAssert.fail("Failure was expected");
        }
    }
}
