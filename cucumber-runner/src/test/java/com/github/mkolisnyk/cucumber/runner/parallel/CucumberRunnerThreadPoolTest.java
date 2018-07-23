package com.github.mkolisnyk.cucumber.runner.parallel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;

public class CucumberRunnerThreadPoolTest {

    private static List<String> messages = new ArrayList<>();
    public class TestThread implements Runnable {

        @Override
        public void run() {
            long id = (new Date()).getTime();
            for (int i = 0; i < 10; i++) {
                try {
                    messages.add("" + id + ": message");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            messages.add("" + id + ": ended");
        }
    }

    @Test
    public void testVolumeOfThreads() throws Exception {
        CucumberRunnerThreadPool.setCapacity(8);
        for (int i = 0; i < 10; i++) {
            TestThread thread = new TestThread();
            CucumberRunnerThreadPool.get().push(new Thread(thread));
            Thread.sleep(1231);
        }
        CucumberRunnerThreadPool.get().waitEmpty();
        Assert.assertEquals(messages.size(), 110);
    }
    @Test
    public void testPoolEmptyForNegativeCapacity() {
        CucumberRunnerThreadPool.setCapacity(1);
        Assert.assertTrue(CucumberRunnerThreadPool.get().isEmpty());
    }
    @Test
    public void testPoolForDryRun() throws Exception {
        CucumberRunnerThread thread = new CucumberRunnerThread(
                new ExtendedCucumber(
                        CucumberRunnerThreadTest.class),
                new RunNotifier());
        CucumberRunnerThreadPool.setCapacity(1);
        CucumberRunnerThreadPool.get().push(new Thread(thread));
        CucumberRunnerThreadPool.get().waitEmpty();
    }
}
