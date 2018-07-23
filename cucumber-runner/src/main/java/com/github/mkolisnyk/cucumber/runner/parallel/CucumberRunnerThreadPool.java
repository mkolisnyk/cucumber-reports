package com.github.mkolisnyk.cucumber.runner.parallel;

import java.util.ArrayList;
import java.util.List;

public final class CucumberRunnerThreadPool {
    private static final int MILLS_PER_SECOND = 1000;
    private static CucumberRunnerThreadPool instance;
    private int maxCapacity = 1;
    private List<Thread> threadList;
    private CucumberRunnerThreadPool() {
        threadList = new ArrayList<>();
    }
    public boolean push(Thread thread) throws Exception {
        waitAvailable();
        thread.start();
        this.getThreadList().add(thread);
        return true;
    }
    public boolean isEmpty() {
        return this.isAvailable() && this.getThreadList().size() <= 0;
    }
    public boolean isAvailable() {
        if (maxCapacity < 1) {
            return false;
        }
        for (int i = 0; i < this.getThreadList().size(); i++) {
            if (!this.getThreadList().get(i).isAlive()) {
                this.getThreadList().remove(i);
                i--;
            }
        }
        return this.getThreadList().size() < this.getMaxCapacity();
    }
    public void waitAvailable() throws InterruptedException {
        while (!this.isAvailable()) {
            Thread.sleep(MILLS_PER_SECOND);
        }
    }
    public void waitEmpty() throws InterruptedException {
        while (!this.isEmpty()) {
            Thread.sleep(MILLS_PER_SECOND);
        }
    }
    public List<Thread> getThreadList() {
        return threadList;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacityValue) {
        this.maxCapacity = maxCapacityValue;
    }
    public static CucumberRunnerThreadPool get() {
        if (instance == null) {
            instance = new CucumberRunnerThreadPool();
        }
        return instance;
    }
    public static void setCapacity(int value) {
        get().setMaxCapacity(value);
    }
}
