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
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

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

    public CommandsTest() {

    }

    @Before
    public void setUp() throws MalformedURLException {
        File dataFile = new File("testData" + File.separator + "testMaterial.html");
        path = "file://" + dataFile.getAbsolutePath();
        executor = new Executor(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
        executor.get(this.path);
    }

    @After
    public void tearDown() {
        executor.close();
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