package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class FreemarkerConfigurationGetResourceMapFromFileTest {
    private String location;
    private Map<String, String> expectedMap;
    
    public FreemarkerConfigurationGetResourceMapFromFileTest(String location,
            Map<String, String> expectedMap) {
        super();
        this.location = location;
        this.expectedMap = expectedMap;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> getParameters() throws Exception {
        return Arrays.asList(new Object[][] {
                {"non_existing_path", FreemarkerConfiguration.getResourceMap("")},
                {"/templates/single_ne_tmpl.json", FreemarkerConfiguration.getResourceMap("")},
                {"/templates/single_tmpl.json",
                    new HashMap<String, String>() {
                        {
                            putAll(FreemarkerConfiguration.getResourceMap(""));
                            put("overview", "/templates/test/overview.ftlh");
                        }
                    }
                },
                {"/templates/extra_tmpl.json",
                    new HashMap<String, String>() {
                        {
                            putAll(FreemarkerConfiguration.getResourceMap(""));
                            put("extra", "/templates/test/overview.ftlh");
                        }
                    }
                },
                {"/templates/extra_ne_tmpl.json", FreemarkerConfiguration.getResourceMap("")},
        });
    }
    @Test
    public void testGetResourceMapFromConfigFile() throws Exception {
        Map<String, String> actualMap = FreemarkerConfiguration.getResourceMap(location);
        for (Entry<String, String> entry : expectedMap.entrySet()) {
            Assert.assertTrue("Unable to find key " + entry.getKey(), actualMap.containsKey(entry.getKey()) );
            Assert.assertEquals(
                    "The " + entry.getKey() + " values mismatch",
                    entry.getValue(),
                    actualMap.get(entry.getKey()));
        }
    }
}
