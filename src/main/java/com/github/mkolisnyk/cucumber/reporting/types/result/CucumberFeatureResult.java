package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberAggregatedDuration;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepDuration;

public class CucumberFeatureResult {
    private String                   id;
    private CucumberTagResults[]     tags;
    private String                   description;
    private String                   name;
    private String                   keyword;
    private Long                      line;
    private CucumberScenarioResult[] elements;
    private String                   uri;

    public CucumberFeatureResult(JsonObject<String, Object> json) {
        this.id = (String)json.get("id");
        Object[] objs = (Object[])((JsonObject<String, Object>)json.get("tags")).get("@items");
        this.tags = new CucumberTagResults[objs.length];
        for(int i=0;i<objs.length;i++){
            this.tags[i] = new CucumberTagResults((JsonObject<String, Object>)objs[i]);
        }
        this.description = (String)json.get("description");
        this.name = (String)json.get("name");
        this.keyword = (String)json.get("keyword");
        this.line = (Long)json.get("line");
        objs = (Object[])((JsonObject<String, Object>)json.get("elements")).get("@items");
        this.elements = new CucumberScenarioResult[objs.length];
        for(int i=0;i<objs.length;i++){
            this.elements[i] = new CucumberScenarioResult((JsonObject<String, Object>)objs[i]);
        }
        this.uri = (String)json.get("uri");
    }

    private int passed = 0;
    private int failed = 0;
    private int undefined = 0;

    public void valuate() {
        for (CucumberScenarioResult scenario : elements) {
            scenario.valuate();
            if (scenario.getFailed() > 0) {
                this.failed++;
            } else if (scenario.getUndefined() > 0) {
                this.undefined++;
            } else {
                this.passed++;
            }
        }
    }
    
    public String getStatus() {
        this.valuate();
        if (this.getFailed() > 0 ) {
            return "failed";
        } else if (this.getUndefined() > 0) {
            return "undefined";
        } else {
            return "passed";
        }
    }
    
    /**
     * @return the passed
     */
    public final int getPassed() {
        return passed;
    }

    /**
     * @return the failed
     */
    public final int getFailed() {
        return failed;
    }

    /**
     * @return the undefined
     */
    public final int getUndefined() {
        return undefined;
    }

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @param idValue
     *            the id to set
     */
    public final void setId(String idValue) {
        this.id = idValue;
    }

    /**
     * @return the tags
     */
    public final CucumberTagResults[] getTags() {
        return tags;
    }

    /**
     * @param tagsValue
     *            the tags to set
     */
    public final void setTags(CucumberTagResults[] tagsValue) {
        this.tags = tagsValue;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param descriptionValue
     *            the description to set
     */
    public final void setDescription(String descriptionValue) {
        this.description = descriptionValue;
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
     * @return the elements
     */
    public final CucumberScenarioResult[] getElements() {
        return elements;
    }

    /**
     * @param elementsValue
     *            the elements to set
     */
    public final void setElements(CucumberScenarioResult[] elementsValue) {
        this.elements = elementsValue;
    }

    /**
     * @return the uri
     */
    public final String getUri() {
        return uri;
    }

    /**
     * @param uriValue
     *            the uri to set
     */
    public final void setUri(String uriValue) {
        this.uri = uriValue;
    }
}
