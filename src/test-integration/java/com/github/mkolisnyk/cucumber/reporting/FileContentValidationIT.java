package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.github.mkolisnyk.cucumber.reporting.utils.helpers.FolderUtils;


public class FileContentValidationIT {

    private Map<String, String> comparisonMasks = new HashMap<String, String>() {
    };

    @Ignore
    @Test
    public void testValidate() throws Exception {
        String expectedFolder = "src/test-integration/resources/file-comparison/";
        String actualFolder = "target" + File.separator;
        String[] files = FolderUtils.getFilesByMask(expectedFolder, "(.*)html");
        for (String file : files) {
            File expectedFolderPath = new File(expectedFolder);
            String fileNameTail = (new File(file)).getAbsolutePath()
                    .replace(expectedFolderPath.getAbsolutePath(), "");

            String actualFile = actualFolder + fileNameTail;
            System.out.println("Verifying file: " + actualFile);
            String expectedContent = FileUtils.readFileToString(new File(file), "UTF-8");
            String actualContent = FileUtils.readFileToString(new File(actualFile), "UTF-8");
            Assert.assertEquals(String.format("Target file '%s' has unexpected content.", actualFile),
                expectedContent, actualContent);
        }
    }
}
