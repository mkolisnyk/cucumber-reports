/**
 * 
 */
package com.github.mkolisnyk.cucumber.reporting.types.usage;

import com.cedarsoftware.util.io.JsonObject;

/**
 * @author Myk Kolisnyk
 *
 */
public class CucumberStepSource {
    /**
     * .
     */
    private String source;
    /**
     * .
     */
    private CucumberStep[] steps;
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }
    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
    /**
     * @return the steps
     */
    public CucumberStep[] getSteps() {
        return steps;
    }
    /**
     * @param steps the steps to set
     */
    public void setSteps(CucumberStep[] steps) {
        this.steps = steps;
    }
    /**
     * @param source
     * @param steps
     */
    public CucumberStepSource(String source, CucumberStep[] steps) {
        super();
        this.source = source;
        this.steps = steps;
    }
    
    public CucumberStepSource(JsonObject<String,Object> json){
        this.source = (String)json.get("source");
        Object[] objs = (Object[])((JsonObject<String,Object>)json.get("steps")).get("@items");
        this.steps = new CucumberStep[objs.length];
        for(int i=0;i<objs.length;i++){
            this.steps[i] = new CucumberStep((JsonObject<String,Object>)objs[i]);
        }
    }
}
