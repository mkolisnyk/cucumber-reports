package com.github.mkolisnyk.cucumber.reporting.types.result;

import java.util.LinkedHashMap;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberStepResult {

    private CucumberResult result;
    private String         name;
    private String         keyword;
    private Long           line;
    private String[][] rows;

    @SuppressWarnings("unchecked")
    public CucumberStepResult(JsonObject<String, Object> json) {
        this.name = (String) json.get("name");
        this.keyword = (String) json.get("keyword");
        this.line = (Long) json.get("line");
        this.result = new CucumberResult(
                (JsonObject<String, Object>) json.get("result"));
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
    }

    /**
     * @return the result
     */
    public final CucumberResult getResult() {
        return result;
    }

    /**
     * @param resultValue
     *            the result to set
     */
    public final void setResult(CucumberResult resultValue) {
        this.result = resultValue;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param nameValue
     *            the name to set
     */
    public final void setName(String nameValue) {
        this.name = nameValue;
    }

    /**
     * @return the keyword
     */
    public final String getKeyword() {
        return keyword;
    }

    /**
     * @param keywordValue
     *            the keyword to set
     */
    public final void setKeyword(String keywordValue) {
        this.keyword = keywordValue;
    }

    /**
     * @return the line
     */
    public final Long getLine() {
        return line;
    }

    /**
     * @param lineValue
     *            the line to set
     */
    public final void setLine(Long lineValue) {
        this.line = lineValue;
    }
    /**
     * @return the rows
     */
    public final String[][] getRows() {
        return rows;
    }

    /**
     * @param rowsValue the rows to set
     */
    public final void setRows(String[][] rowsValue) {
        this.rows = rowsValue;
    }

}
