/**
 * .
 */
package com.github.mkolisnyk.cucumber.formatter;

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

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Myk Kolisnyk
 */
public class DBFormatter implements Formatter, Reporter {

    private String connectionString;

    public DBFormatter(File output) throws IOException {
        this.connectionString = output.getName();
    }

    @SafeVarargs
    final ResultSet executeQuery(String query,
            Pair<Integer, Object>... params) {
        Connection connection;
        ResultSet result = null;
        /*try {
            connection = DriverManager.getConnection(connectionString);
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            int paramIndex = 1;
            for (Pair<Integer, Object> param : params) {
                int type = param.getKey();
                Object value = param.getValue();
                switch (type) {
                case Types.BIGINT:
                case Types.INTEGER:
                    statement.setInt(paramIndex, (Integer) value);
                    break;
                case Types.VARCHAR:
                default:
                    statement.setString(paramIndex, (String) value);
                    break;
                }
                paramIndex++;
            }
            result = statement.executeQuery();
            statement.close();
            connection.close();
            connection = null;
        } catch (SQLException e) {
            return null;
        }*/
        return result;
    }

    @Override
    public void before(Match match, Result result) {
        System.out.println("[BEFORE]");
    }

    @Override
    public void result(Result result) {
        System.out.println("[RESULT]");
    }

    @Override
    public void after(Match match, Result result) {
        System.out.println("[AFTER]");
    }

    @Override
    public void match(Match match) {
        System.out.println("[MATCH]");
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
        System.out.println("");
    }

    @Override
    public void write(String text) {
        System.out.println("[WRITE] " + text);
    }

    @Override
    public void syntaxError(String state, String event,
            List<String> legalEvents, String uri, Integer line) {
        System.out.println("[SYNTAX ERROR]");
    }

    @Override
    public void uri(String uri) {
        System.out.println("[URI]" + uri);
    }

    @Override
    public void feature(Feature feature) {
        System.out.println("[FEATURE]");
        this.executeQuery("EXEC [cukes].[StartFeature] ?,?,?",
                new ImmutablePair<Integer, Object>(Types.VARCHAR, feature.getId()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, feature.getName()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, feature.getDescription())
        );
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        System.out.println("[SCENARIO OUTLINE]");
    }

    @Override
    public void examples(Examples examples) {
        System.out.println("[EXAMPLES]");
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("[START SCENARIO]");
    }

    @Override
    public void background(Background background) {
        System.out.println("[BACKGROUND]");
    }

    @Override
    public void scenario(Scenario scenario) {
        System.out.println(String.format("[SCENARIO] id: %s .. Name: %s",
                scenario.getId(), scenario.getName()));
        this.executeQuery("EXEC [cukes].[StartScenario] ?,?,?,?",
                new ImmutablePair<Integer, Object>(Types.VARCHAR, scenario.getId()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, scenario.getName()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, scenario.getDescription()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, scenario.getKeyword())
        );
    }

    @Override
    public void step(Step step) {
        System.out.println(String.format("[STEP] %s", step.getName()));
        /*this.executeQuery("EXEC [cukes].[StartScenario] ?,?,?,?",
                new ImmutablePair<Integer, Object>(Types.VARCHAR, step.getId()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, scenario.getName()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, scenario.getDescription()),
                new ImmutablePair<Integer, Object>(Types.VARCHAR, scenario.getKeyword())
        );*/
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("[END SCENARIO]");
    }

    @Override
    public void done() {
        System.out.println("[DONE]");
    }

    @Override
    public void close() {
        System.out.println("[CLOSE]");
    }

    @Override
    public void eof() {
        System.out.println("[EOF]");
    }
}
