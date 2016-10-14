package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;

public final class FolderUtils {
    private FolderUtils() {
    }
    private String[] getFileNames(String rootFolder) throws Exception {
        String[] fileNames = {};
        for (File file : new File(rootFolder).listFiles()) {
            if (file.isDirectory()) {
                fileNames = (String[]) ArrayUtils.addAll(fileNames, getFileNames(file.getAbsolutePath()));
            } else {
                fileNames = (String[]) ArrayUtils.add(fileNames, file.getAbsolutePath());
            }
        }
        return fileNames;
    }
    public static String[] getFilesByMask(String startFolder, String mask) throws Exception {
        String[] result = {};
        FolderUtils utils = new FolderUtils();
        String[] input = utils.getFileNames(startFolder);
        for (String fileName : input) {
            if (fileName.matches(mask)) {
                result = (String[]) ArrayUtils.add(result, fileName);
            }
        }
        return result;
    }

}
