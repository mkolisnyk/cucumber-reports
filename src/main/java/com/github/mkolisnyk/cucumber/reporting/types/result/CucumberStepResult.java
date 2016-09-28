package com.github.mkolisnyk.cucumber.reporting.types.result;

import java.util.LinkedHashMap;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberStepResult {

    private CucumberResult result;
    private String         name;
    private String         keyword;
    //private Long           line;
    private String[][] rows;
    private String docString;
    private CucumberEmbedding[] embeddings;
    private String[] output;

    @SuppressWarnings("unchecked")
    public CucumberStepResult(JsonObject<String, Object> json) {
        this.name = (String) json.get("name");
        this.keyword = (String) json.get("keyword");
        //this.line = (Long) json.get("line");
        this.result = new CucumberResult(
                (JsonObject<String, Object>) json.get("result"));
        if (json.containsKey("embeddings")) {
            Object[] objs = (Object[]) ((JsonObject<String, Object>) json
                    .get("embeddings")).get("@items");
            this.embeddings = new CucumberEmbedding[objs.length];
            for (int i = 0; i < objs.length; i++) {
                this.embeddings[i] = new CucumberEmbedding((JsonObject<String, Object>) objs[i]);
            }
        }
        if (json.containsKey("output")) {
            Object[] objs = (Object[]) ((JsonObject<String, Object>) json
                    .get("output")).get("@items");
            this.output = new String[objs.length];
            for (int i = 0; i < objs.length; i++) {
                this.output[i] = (String) objs[i];
            }
        }
        if (json.containsKey("rows")) {
            Object[] objs = (Object[]) ((JsonObject<String, Object>) json
                    .get("rows")).get("@items");
            this.rows = new String[objs.length][];
            for (int i = 0; i < objs.length; i++) {
                Object[] row = (Object[]) ((JsonObject<String, Object>)
                        ((LinkedHashMap<String, Object>) objs[i]).get("cells")).get("@items");
                this.rows[i] = new String[row.length];
                for (int j = 0; j < row.length; j++) {
                    this.rows[i][j] = (String) row[j];
                }
            }
        }
        if (json.containsKey("doc_string")) {
            this.docString = (String) ((JsonObject<String, Object>) json
                    .get("doc_string")).get("value");
        }
    }

    /**
     * @return the result
     */
    public final CucumberResult getResult() {
        return result;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @return the keyword
     */
    public final String getKeyword() {
        return keyword;
    }

    /**
     * @return the line
     */
    //public final Long getLine() {
    //    return line;
    //}

    /**
     * @return the rows
     */
    public final String[][] getRows() {
        return rows;
    }

    public final String getDocString() {
        return docString;
    }

    public CucumberEmbedding[] getEmbeddings() {
        return embeddings;
    }

    public String[] getOutput() {
        return output;
    }
}
