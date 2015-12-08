package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

public class BreakdownStats {
    private int passed;
    private int failed;
    private int skipped;
    public BreakdownStats() {
        this.passed = 0;
        this.failed = 0;
        this.skipped = 0;
    }
    public int getPassed() {
        return passed;
    }
    public int getFailed() {
        return failed;
    }
    public int getSkipped() {
        return skipped;
    }
    public void addPassed() {
        this.passed++;
    }
    public void addFailed() {
        this.failed++;
    }
    public void addSkipped() {
        this.skipped++;
    }
}
