package com.github.mkolisnyk.cucumber.runner;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class CLIRunnerTest {

    @Test
    public void testRunCommand() throws Throwable {
        CLIRunner.main(new String[] {"--help"});
    }
}
