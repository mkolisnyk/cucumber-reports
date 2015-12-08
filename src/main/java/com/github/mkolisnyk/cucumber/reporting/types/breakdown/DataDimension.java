package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

public class DataDimension {

    private String alias;
    private DimensionValue dimensionValue;
    private String expression;
    private DataDimension[] subElements;

    public static DataDimension allFeatures() {
        return new DataDimension("Features", DimensionValue.FEATURE);
    }
    public static DataDimension allScenarios() {
        return new DataDimension("Scenarios", DimensionValue.SCENARIO);
    }
    public static DataDimension allSteps() {
        return new DataDimension("Steps", DimensionValue.STEP);
    }
    public static DataDimension allTags() {
        return new DataDimension("Tags", DimensionValue.TAG);
    }

    public DataDimension(DimensionValue dimensionValueParam) {
        this(dimensionValueParam, "(.*)");
    }
    public DataDimension(DimensionValue dimensionValueParam, String expressionParam) {
        this(dimensionValueParam, expressionParam, new DataDimension[]{});
    }
    public DataDimension(DimensionValue dimensionValueParam, String expressionParam,
            DataDimension[] subElementsParam) {
        this(expressionParam, dimensionValueParam, expressionParam, subElementsParam);
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam) {
        this(aliasParam, dimensionValueParam, "(.*)");
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam,
            String expressionParam) {
        this(aliasParam, dimensionValueParam, expressionParam, new DataDimension[]{});
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam,
            String expressionParam, DataDimension[] subElementsParam) {
        super();
        this.alias = aliasParam;
        this.dimensionValue = dimensionValueParam;
        this.expression = expressionParam;
        this.subElements = subElementsParam;
    }
    public String getAlias() {
        return alias;
    }
    public DimensionValue getDimensionValue() {
        return dimensionValue;
    }
    public String getExpression() {
        return expression;
    }
    public DataDimension[] getSubElements() {
        return subElements;
    }
    public int depth() {
        int depth = 1;
        if (this.subElements == null || this.subElements.length == 0) {
            return depth;
        }
        int subDepth = 0;
        for (DataDimension dimension : this.subElements) {
            subDepth = Math.max(subDepth, dimension.depth());
        }
        depth += subDepth;
        return depth;
    }
}
