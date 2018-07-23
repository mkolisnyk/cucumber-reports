package com.github.mkolisnyk.cucumber.utils;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Assert;

public class TestUtils {

    public static void verifyXpathValue(String input, String xpath, String value) throws Exception {
        TagNode tagNode = new HtmlCleaner().clean(
                input);
        org.w3c.dom.Document doc = new DomSerializer(
                new CleanerProperties()).createDOM(tagNode);

        XPath xpathValue = XPathFactory.newInstance().newXPath();
        String str = (String) xpathValue.evaluate(xpath, 
                               doc, XPathConstants.STRING);
        System.out.println(str);
        Assert.assertEquals("Unexpected '" + xpath + "' xpath value", value, str);
    }
}
