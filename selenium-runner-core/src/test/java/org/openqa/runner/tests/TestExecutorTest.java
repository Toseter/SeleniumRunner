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

package org.openqa.runner.tests;

import junit.framework.JUnit4TestAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 24.06.11
 */
public class TestExecutorTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TestExecutorTest.class);
    }

    private Executor _testExecutor;
    private WebDriver webDriver;

    @Before
    public void setUp() {
        Capabilities desiredCapabilities = DesiredCapabilities.firefox();
        try {
            _testExecutor = new Executor(new URL("http://localhost:4444/wd/hub"), desiredCapabilities);
            Field webDriverField = _testExecutor.getClass().getDeclaredField("webDriver");
            webDriverField.setAccessible(true);
            webDriver = (WebDriver) webDriverField.get(_testExecutor);
        } catch (Exception ex) {
            System.err.print("Some error");
        }
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }

    @Test
    public void testTestSuiteExecuting() {
        org.openqa.runner.tests.Test[] tests = new org.openqa.runner.tests.Test[3];
        for (int i = 0; i < tests.length; i++) {
            tests[i] = new org.openqa.runner.tests.Test();

            Map<String, String> params = new HashMap<String, String>();
            params.put("url", "http://google.com");
            Command command = new Command("open", params);

            tests[i].addCommand(command);

            params = new HashMap<String, String>();
            params.put("url", "http://yandex.ru");
            command = new Command("open", params);

            tests[i].addCommand(command);

        }

        Suite testSuite = new Suite(tests);

        SuiteResult testResult = _testExecutor.execute(testSuite);

    }
}