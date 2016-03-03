package com.github.mkolisnyk.cucumber.runner.parallel;

import java.util.ArrayList;
import java.util.List;

public final class CucumberRunnerThreadPool {
    private static CucumberRunnerThreadPool instance;
    private int maxCapacity = 1;
    private List<Thread> threadList;
    private CucumberRunnerThreadPool() {
        threadList = new ArrayList<Thread>();
    }
    public boolean push(Thread thread) {
        waitAvailable();
        thread.start();
        this.getThreadList().add(thread);
        return true;
    }
    public boolean isEmpty() {
        return this.isAvailable() && this.getThreadList().size() <= 0;
    }
    public boolean isAvailable() {
        for (int i = 0; i < this.getThreadList().size(); i++) {
            if (!this.getThreadList().get(i).isAlive()) {
                System.out.println("Removing thread from pool");
                this.getThreadList().remove(i);
                i--;
            }
        }
        return this.getThreadList().size() < this.getMaxCapacity();
    }
    public void waitAvailable() {
        while (!this.isAvailable()) {}
    }
    public void waitEmpty() {
        while (!this.isEmpty()) {}
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
