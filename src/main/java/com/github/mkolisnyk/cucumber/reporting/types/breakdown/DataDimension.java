package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import org.apache.commons.lang.ArrayUtils;

public class DataDimension {

    private String alias;
    private DimensionValue dimensionValue;
    private String expression;
    private DataDimension[] subElements;
    private boolean isFinal = false;

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
    public DataDimension(DimensionValue dimensionValueParam, boolean isFinalValue) {
        this(dimensionValueParam, "(.*)", isFinalValue);
    }
    public DataDimension(DimensionValue dimensionValueParam, String expressionParam) {
        this(dimensionValueParam, expressionParam, new DataDimension[]{});
    }
    public DataDimension(DimensionValue dimensionValueParam, String expressionParam, boolean isFinalValue) {
        this(dimensionValueParam, expressionParam, new DataDimension[]{}, isFinalValue);
    }
    public DataDimension(DimensionValue dimensionValueParam, String expressionParam,
            DataDimension[] subElementsParam) {
        this(expressionParam, dimensionValueParam, expressionParam, subElementsParam);
    }
    public DataDimension(DimensionValue dimensionValueParam, String expressionParam,
            DataDimension[] subElementsParam, boolean isFinalValue) {
        this(expressionParam, dimensionValueParam, expressionParam, subElementsParam, isFinalValue);
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam) {
        this(aliasParam, dimensionValueParam, "(.*)");
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam, boolean isFinalValue) {
        this(aliasParam, dimensionValueParam, "(.*)", isFinalValue);
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam,
            String expressionParam) {
        this(aliasParam, dimensionValueParam, expressionParam, new DataDimension[]{});
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam,
            String expressionParam, boolean isFinalValue) {
        this(aliasParam, dimensionValueParam, expressionParam, new DataDimension[]{}, isFinalValue);
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam,
            String expressionParam, DataDimension[] subElementsParam) {
        this(aliasParam, dimensionValueParam, expressionParam, subElementsParam, false);
    }
    public DataDimension(String aliasParam, DimensionValue dimensionValueParam,
            String expressionParam, DataDimension[] subElementsParam, boolean isFinalValue) {
        super();
        this.alias = aliasParam;
        this.dimensionValue = dimensionValueParam;
        this.expression = expressionParam;
        this.subElements = subElementsParam;
        this.isFinal = isFinalValue;
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
    public boolean hasSubElements() {
        return !isFinal() && this.subElements != null && this.subElements.length > 0;
    }
    public int depth() {
        int depth = 1;
        if (!hasSubElements()) {
            return depth;
        }
        int subDepth = 0;
        for (DataDimension dimension : this.subElements) {
            subDepth = Math.max(subDepth, dimension.depth());
        }
        depth += subDepth;
        return depth;
    }
    public int width() {
        int width = 0;
        if (!hasSubElements()) {
            return 1;
        }
        for (DataDimension dimension : this.subElements) {
            width += dimension.width();
        }
        return width;
    }
    public DataDimension[][] expand() {
        if (!hasSubElements()) {
            return new DataDimension[][] {{this}};
        }
        DataDimension[][] result = {};
        for (DataDimension item : this.getSubElements()) {
            DataDimension[][] subTree = item.expand();
            for (DataDimension[] subTreeLine : subTree) {
                DataDimension[] line = new DataDimension[subTreeLine.length + 1];
                line[0] = this;
                for (int i = 0; i < subTreeLine.length; i++) {
                    line[i + 1] = subTreeLine[i];
                }
                result = (DataDimension[][]) ArrayUtils.add(result, line);
            }
        }
        return result;
    }
    public DataDimension[] getRow(int level) {
        if (level == 0) {
            return new DataDimension[] {this};
        }
        if (!this.hasSubElements()) {
            return new DataDimension[] {};
        }
        DataDimension[] result = new DataDimension[] {};
        for (DataDimension subItem : this.getSubElements()) {
            result = (DataDimension[]) ArrayUtils.addAll(result, subItem.getRow(level - 1));
        }
        return result;
    }
    public boolean isFinal() {
        return isFinal;
    }
}
