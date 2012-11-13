/*
 * Copyright 2011 Aleksey Shilin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.openqa.runner;

import junit.framework.JUnit4TestAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.runner.tests.Command;
import org.openqa.runner.tests.Executor;
import org.openqa.runner.tests.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 20.07.11
 */
public class CommandsTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CommandsTest.class);
    }

    Executor executor;
    String path;
    String basePath;

    public CommandsTest() {

    }

    private WebDriver webDriver;

    @Before
    public void setUp() throws Exception {
        URL fileToTestMaterial =  this.getClass().getClassLoader().getResource("testMaterial.html");
        File dataFile = new File(fileToTestMaterial.toURI());
        path = "file://" + dataFile.getAbsolutePath();
        basePath = "file://" + (new File(dataFile.getParent())).getAbsolutePath() + "/";
        executor = new Executor(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
        try {
            Field webDriverField = executor.getClass().getDeclaredField("webDriver");
            webDriverField.setAccessible(true);
            webDriver = (WebDriver) webDriverField.get(executor);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        webDriver.get(this.path);
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }

    @Test
    public void testOpen() {
        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("url", basePath + "testMaterial2.html");
        test.addCommand(new Command("open", params));
        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test});
        executor.execute(suite);
        assertEquals("testMaterial2", webDriver.getTitle());
    }

    @Test
    public void testOpenRelativeUrl() {
        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("url", "/testMaterial2.html");
        test.addCommand(new Command("open", params));
        test.setBaseUrl(basePath);
        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test});
        executor.execute(suite);
        assertEquals("testMaterial2", webDriver.getTitle());
    }

    @Test
    public void testClick() {
        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testClick");
        test.addCommand(new Command("click", params));
        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test});
        executor.execute(suite);
        assertEquals("testMaterial2", webDriver.getTitle());
    }

    @Test
    public void testType() {
        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testType");
        params.put("text", "something");
        test.addCommand(new Command("type", params));
        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test});
        executor.execute(suite);
        assertEquals("something", webDriver.findElement(By.name("testType")).getAttribute("value"));
    }

    @Test
    public void testAssertText() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testAssertText");
        params.put("text", "something");
        test.addCommand(new Command("assertText", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "testAssertText");
        params.put("text", "else");
        test2.addCommand(new Command("assertText", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isAborted());
        assertTrue(test2.getState().isAborted());
    }

    @Test
    public void testAssertTextPresent() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("text", "something");
        test.addCommand(new Command("assertTextPresent", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("text", "No_this_text");
        test2.addCommand(new Command("assertTextPresent", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isAborted());
        assertTrue(test2.getState().isAborted());
    }

    @Test
    public void testAssertValue() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testAssertValue");
        params.put("value", "something");
        test.addCommand(new Command("assertValue", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "testAssertValue");
        params.put("value", "else");
        test2.addCommand(new Command("assertValue", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isAborted());
        assertTrue(test2.getState().isAborted());
    }

    @Test
    public void testAssertTable() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "id=testAssertTable.0.0");
        params.put("value", "something");
        test.addCommand(new Command("assertTable", params));
        params.put("target", "id=testAssertTable.1.0");
        params.put("value", "else");
        test.addCommand(new Command("assertTable", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "id=testAssertTable.0.0");
        params.put("value", "else");
        test2.addCommand(new Command("assertTable", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isAborted());
        assertTrue(test2.getState().isAborted());
    }

    @Test
    public void testAssertElementPresent() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testAssertElementPresent");
        test.addCommand(new Command("assertElementPresent", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "no_element");
        test2.addCommand(new Command("assertElementPresent", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isAborted());
        assertTrue(test2.getState().isAborted());
    }

    @Test
    public void testVerifyText() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testVerifyText");
        params.put("text", "something");
        test.addCommand(new Command("verifyText", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "testVerifyText");
        params.put("text", "else");
        test2.addCommand(new Command("verifyText", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isFailed());
        assertTrue(test2.getState().isFailed());
    }

    @Test
    public void testVerifyTextPresent() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("text", "something");
        test.addCommand(new Command("verifyTextPresent", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("text", "No_this_text");
        test2.addCommand(new Command("verifyTextPresent", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isFailed());
        assertTrue(test2.getState().isFailed());
    }

    @Test
    public void testVerifyValue() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testVerifyValue");
        params.put("value", "something");
        test.addCommand(new Command("verifyValue", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "testVerifyValue");
        params.put("value", "else");
        test2.addCommand(new Command("verifyValue", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isFailed());
        assertTrue(test2.getState().isFailed());
    }

    @Test
    public void testVerifyTable() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "id=testVerifyTable.0.0");
        params.put("value", "something");
        test.addCommand(new Command("verifyTable", params));
        params.put("target", "id=testVerifyTable.1.0");
        params.put("value", "else");
        test.addCommand(new Command("verifyTable", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "id=testVerifyTable.0.0");
        params.put("value", "else");
        test2.addCommand(new Command("verifyTable", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isFailed());
        assertTrue(test2.getState().isFailed());
    }

    @Test
    public void testVerifyElementPresent() {

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testVerifyElementPresent");
        test.addCommand(new Command("verifyElementPresent", params));

        org.openqa.runner.tests.Test test2 = new org.openqa.runner.tests.Test();
        params = new HashMap<String, String>();
        params.put("target", "no_element");
        test2.addCommand(new Command("verifyElementPresent", params));

        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test, test2});
        executor.execute(suite);
        assertFalse(test.getState().isFailed());
        assertTrue(test2.getState().isFailed());
    }


    @Test
    public void testStoreText() {
        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testStoreText");
        params.put("text", "textVar");
        test.addCommand(new Command("storeText", params));
        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test});
        executor.execute(suite);
        assertEquals("something", test.getState().getVariable("textVar"));
    }

    @Test
    public void testStoreTitle() {
        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("title", "titleVar");
        test.addCommand(new Command("storeTitle", params));
        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test});
        executor.execute(suite);
        assertEquals("Something", test.getState().getVariable("titleVar"));
    }

    @Test
    public void testStoreValue() {
        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("target", "testStoreValue");
        params.put("value", "valueVar");
        test.addCommand(new Command("storeValue", params));
        Suite suite = new Suite(new org.openqa.runner.tests.Test[]{test});
        executor.execute(suite);
        assertEquals("something", test.getState().getVariable("valueVar"));
    }
}