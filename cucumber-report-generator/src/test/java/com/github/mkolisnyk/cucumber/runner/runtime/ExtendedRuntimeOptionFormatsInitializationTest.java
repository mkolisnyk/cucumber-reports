package com.github.mkolisnyk.cucumber.runner.runtime;

import org.junit.Assert;
import org.junit.Test;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

@ExtendedCucumberOptions(toPDF = true)
@ExtendedCucumberOptions(toPDF = true, formats = {"jpg", "png"})
@ExtendedCucumberOptions(toPDF = true, formats = {"png", "pdf", "jpg"})
@ExtendedCucumberOptions(toPDF = false, formats = {"png", "pdf", "jpg"})
@ExtendedCucumberOptions(toPDF = false)
public class ExtendedRuntimeOptionFormatsInitializationTest {
    public ExtendedCucumberOptions[] options = ExtendedRuntimeOptionFormatsInitializationTest
            .class.getAnnotationsByType(ExtendedCucumberOptions.class);
    
    @Test
    public void testPDFIncludedToEmptyFormats() throws Exception {
        ExtendedRuntimeOptions runtime = new ExtendedRuntimeOptions(options[0]);
        Assert.assertArrayEquals(new String[] {"pdf"}, runtime.getFormats());
    }
    @Test
    public void testPDFIncludedNonEmptyFormats() throws Exception {
        ExtendedRuntimeOptions runtime = new ExtendedRuntimeOptions(options[1]);
        Assert.assertArrayEquals(new String[] {"jpg", "png", "pdf"}, runtime.getFormats());
    }
    @Test
    public void testPDFIncludedAndPresentInFormats() throws Exception {
        ExtendedRuntimeOptions runtime = new ExtendedRuntimeOptions(options[2]);
        Assert.assertArrayEquals(new String[] {"png", "pdf", "jpg"}, runtime.getFormats());
    }
    @Test
    public void testPDFExcludedAndPresentInFormats() throws Exception {
        ExtendedRuntimeOptions runtime = new ExtendedRuntimeOptions(options[3]);
        Assert.assertArrayEquals(new String[] {"png", "jpg"}, runtime.getFormats());
    }
    @Test
    public void testPDFExcludedToEmptyFormats() throws Exception {
        ExtendedRuntimeOptions runtime = new ExtendedRuntimeOptions(options[4]);
        Assert.assertArrayEquals(new String[] {}, runtime.getFormats());
    }
}
