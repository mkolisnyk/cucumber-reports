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
    private String source;
    private CucumberStep[] steps;
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public CucumberStep[] getSteps() {
        return steps;
    }
    public void setSteps(CucumberStep[] steps) {
        this.steps = steps;
    }
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
