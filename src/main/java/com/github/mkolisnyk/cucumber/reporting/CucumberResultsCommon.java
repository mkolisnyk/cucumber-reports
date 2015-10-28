package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;

public class CucumberResultsCommon {
    private String sourceFile;

    /**
     * @return the sourceFile
     */
    public final String getSourceFile() {
        return sourceFile;
    }

    /**
     * @param sourceFileValue the sourceFile to set
     */
    public final void setSourceFile(String sourceFileValue) {
        this.sourceFile = sourceFileValue;
    }

    public CucumberFeatureResult[] aggregateResults(CucumberFeatureResult[] input, boolean collapse) {
        for (int i = 0; i < input.length; i++) {
            input[i].aggregateScenarioResults(collapse);
        }
        return input;
    }

    @SuppressWarnings("unchecked")
    public CucumberFeatureResult[] readFileContent(boolean aggregate) throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(this.getSourceFile());

        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException();
        }

        fis = new FileInputStream(file);
        jr = new JsonReader(fis, true);
        JsonObject<String, Object> source = (JsonObject<String, Object>) jr.readObject();
        Object[] objs = (Object[]) source.get("@items");

        CucumberFeatureResult[] sources = new CucumberFeatureResult[objs.length];
        for (int i = 0; i < objs.length; i++) {
            sources[i] = new CucumberFeatureResult((JsonObject<String, Object>) objs[i]);
        }
        jr.close();
        fis.close();
           sources = aggregateResults(sources, aggregate);
        return sources;
    }

    @SuppressWarnings("unchecked")
    public <T extends CucumberFeatureResult> T[] readFileContent(Class<T> param) throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(this.getSourceFile());

        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException();
        }

        fis = new FileInputStream(file);
        jr = new JsonReader(fis, true);
        JsonObject<String, Object> source = (JsonObject<String, Object>) jr.readObject();
        Object[] objs = (Object[]) source.get("@items");

        T[] sources = (T[]) Array.newInstance(param, objs.length);
        for (int i = 0; i < objs.length; i++) {
            sources[i] = (T) param.getConstructors()[0].newInstance((JsonObject<String, Object>) objs[i]);
        }
        jr.close();
        fis.close();
        return sources;
    }
    public String replaceHtmlEntitiesWithCodes(String input) throws IOException {
        String output = input;
        Map<String, String> entitiesMap = new HashMap<String, String>();
        InputStream is = this.getClass().getResourceAsStream("/html_entities_map.txt");
        String[] result = IOUtils.toString(is).split("\n");
        is.close();
        for (String line : result) {
            entitiesMap.put(line.split("(\\s+)")[0], line.split("(\\s+)")[1]);
        }
        for (Entry<String, String> entry : entitiesMap.entrySet()) {
            output = output.replace(entry.getKey(), entry.getValue());
        }
        return output;
    }
}
