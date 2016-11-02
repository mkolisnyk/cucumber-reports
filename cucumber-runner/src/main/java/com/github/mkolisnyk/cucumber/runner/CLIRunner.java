package com.github.mkolisnyk.cucumber.runner;

import cucumber.api.cli.Main;

public final class CLIRunner {
    private CLIRunner() {
    }
    public static void main(String[] argv) throws Throwable {
        byte exitstatus = Main.run(argv, Thread.currentThread().getContextClassLoader());
        System.exit(exitstatus);
    }
}
