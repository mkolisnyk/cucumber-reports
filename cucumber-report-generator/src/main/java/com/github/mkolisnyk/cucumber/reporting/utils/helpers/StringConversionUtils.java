package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;

public final class StringConversionUtils {

    private StringConversionUtils() {
    }

    public static String transformPathString(String input) {
        String output = input;
        String datePattern = "DATE\\(([^)]+)\\)";
        String varPattern = "\\$\\{(.*)}";
        if (output == null) {
            return null;
        }
        while (output.matches("(.*)" + datePattern + "(.*)")) {
            String format = output.split("DATE\\(")[1].split("\\)")[0].trim();
            String value = DateTimeFormat.forPattern(format)
                .withDefaultYear(new DateTime().get(DateTimeFieldType.yearOfEra())).withLocale(Locale.US)
                .print(new DateTime());
            output = output.replaceFirst(datePattern, value);
        }
        while (output.matches("(.*)" + varPattern + "(.*)")) {
            String name = output.split("\\$\\{")[1].split("}")[0].trim();
            if (StringUtils.isNotBlank(System.getProperty(name))) {
                output = output.replaceFirst(varPattern, System.getProperty(name));
            } else {
                output = output.replaceFirst(varPattern, System.getenv(name));
            }
        }
        return output;
    }
    public static String replaceHtmlEntitiesWithCodes(String input) throws IOException {
        String output = input;
        Map<String, String> entitiesMap = new HashMap<String, String>();
        InputStream is = StringConversionUtils.class.getResourceAsStream("/html_entities_map.txt");
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
