package com.github.mkolisnyk.cucumber.runner;

public class ExtendedScenarioOutlineRunner /*extends Suite*/ {
/*    private final CucumberScenarioOutline cucumberScenarioOutline;
    private final JUnitReporter jUnitReporter;
    private Description description;

    public ExtendedScenarioOutlineRunner(
            Runtime runtimeValue,
            CucumberScenarioOutline cucumberScenarioOutlineValue,
            JUnitReporter jUnitReporterValue,
            int retryCountValue) throws InitializationError {
        super(null, buildRunners(runtimeValue, cucumberScenarioOutlineValue, jUnitReporterValue, retryCountValue));
        this.cucumberScenarioOutline = cucumberScenarioOutlineValue;
        this.jUnitReporter = jUnitReporterValue;
    }

    private static List<Runner> buildRunners(
            Runtime runtime,
            CucumberScenarioOutline cucumberScenarioOutline,
            JUnitReporter jUnitReporter,
            int retryCount) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (CucumberExamples cucumberExamples : cucumberScenarioOutline.getCucumberExamplesList()) {
            runners.add(new ExtendedExamplesRunner(runtime, cucumberExamples, jUnitReporter, retryCount));
        }
        return runners;
    }

    @Override
    public String getName() {
        return cucumberScenarioOutline.getVisualName();
    }

    @Override
    public Description getDescription() {
        if (description == null) {
            description = Description.createSuiteDescription(getName(), cucumberScenarioOutline.getGherkinModel());
            for (Runner child : getChildren()) {
                description.addChild(describeChild(child));
            }
        }
        return description;
    }

    @Override
    public void run(final RunNotifier notifier) {
        //cucumberScenarioOutline.formatOutlineScenario(jUnitReporter);
        super.run(notifier);
    }

    @Override
    protected void runChild(Runner runner, final RunNotifier notifier) {
        super.runChild(runner, notifier);
    }*/
}
