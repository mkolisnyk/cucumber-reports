/**
 * .
 */
package com.github.mkolisnyk.cucumber.formatter;

import java.util.List;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

/**
 * @author Myk Kolisnyk
 *
 */
public class DBFormatter implements Formatter, Reporter {

    /* (non-Javadoc)
     * @see gherkin.formatter.Reporter#before(gherkin.formatter.model.Match, gherkin.formatter.model.Result)
     */
    @Override
    public void before(Match match, Result result) {
        System.out.println("[BEFORE]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Reporter#result(gherkin.formatter.model.Result)
     */
    @Override
    public void result(Result result) {
        System.out.println("[RESULT]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Reporter#after(gherkin.formatter.model.Match, gherkin.formatter.model.Result)
     */
    @Override
    public void after(Match match, Result result) {
        System.out.println("[AFTER]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Reporter#match(gherkin.formatter.model.Match)
     */
    @Override
    public void match(Match match) {
        System.out.println("[MATCH]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Reporter#embedding(java.lang.String, byte[])
     */
    @Override
    public void embedding(String mimeType, byte[] data) {
        System.out.println("");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Reporter#write(java.lang.String)
     */
    @Override
    public void write(String text) {
        System.out.println("[WRITE] " + text);
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#syntaxError(java.lang.String, java.lang.String, java.util.List, java.lang.String, java.lang.Integer)
     */
    @Override
    public void syntaxError(String state, String event,
            List<String> legalEvents, String uri, Integer line) {
        System.out.println("[SYNTAX ERROR]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#uri(java.lang.String)
     */
    @Override
    public void uri(String uri) {
        System.out.println("[URI]" + uri);
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#feature(gherkin.formatter.model.Feature)
     */
    @Override
    public void feature(Feature feature) {
        System.out.println("[FEATURE]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#scenarioOutline(gherkin.formatter.model.ScenarioOutline)
     */
    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        System.out.println("[SCENARIO OUTLINE]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#examples(gherkin.formatter.model.Examples)
     */
    @Override
    public void examples(Examples examples) {
        System.out.println("[EXAMPLES]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#startOfScenarioLifeCycle(gherkin.formatter.model.Scenario)
     */
    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("[START SCENARIO]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#background(gherkin.formatter.model.Background)
     */
    @Override
    public void background(Background background) {
        System.out.println("[BACKGROUND]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#scenario(gherkin.formatter.model.Scenario)
     */
    @Override
    public void scenario(Scenario scenario) {
        System.out.println("[SCENARIO]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#step(gherkin.formatter.model.Step)
     */
    @Override
    public void step(Step step) {
        System.out.println("[STEP]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#endOfScenarioLifeCycle(gherkin.formatter.model.Scenario)
     */
    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("[END SCENARIO]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#done()
     */
    @Override
    public void done() {
        System.out.println("[DONE]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#close()
     */
    @Override
    public void close() {
        System.out.println("[CLOSE]");
    }

    /* (non-Javadoc)
     * @see gherkin.formatter.Formatter#eof()
     */
    @Override
    public void eof() {
        System.out.println("[EOF]");
    }

}
