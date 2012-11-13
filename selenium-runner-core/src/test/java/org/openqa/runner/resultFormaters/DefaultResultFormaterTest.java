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

package org.openqa.runner.resultFormaters;

import junit.framework.JUnit4TestAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.runner.tests.Command;
import org.openqa.runner.tests.Suite;
import org.openqa.runner.tests.SuiteResult;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 04.08.11
 */
public class DefaultResultFormaterTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DefaultResultFormaterTest.class);
    }

    private Suite testSuite;
    private SuiteResult suiteResult;
    private Command firstCommand, secondCommand;

    @Before
    public void setUp() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("url", "http://google.com");
        firstCommand = new Command("open", params);
        secondCommand = new Command("open", params);

        org.openqa.runner.tests.Test[] tests = new org.openqa.runner.tests.Test[3];
        for (int i = 0; i < tests.length; i++) {
            tests[i] = new org.openqa.runner.tests.Test();
            tests[i].getState().setLastCommand(firstCommand);
            tests[i].getState().setLastCommand(secondCommand);

        }
        tests[0].getState().setAborted();

        testSuite = new Suite(tests);
        suiteResult = new SuiteResult(testSuite);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSomething() throws FileNotFoundException, UnsupportedEncodingException {
        DefaultResultFormater defaultResultFormater = new DefaultResultFormater();
        defaultResultFormater.AddSuiteResult(suiteResult);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String expectedString = "[anonymous]\r\n" +
                "Tests executed:3\r\n" +
                "Tests failed:1\r\n" +
                "\r\n" +
                "{anonymous}\r\n" +
                "0: <open> url='http://google.com'\r\n" +
                "1: <open> url='http://google.com'\r\n\r\n\r\n\n";

        PrintStream out = new PrintStream(output);
        PrintStream backUp = System.out;
        System.setOut(out);
        defaultResultFormater.Process();
        System.setOut(backUp);

        String outputString = output.toString("UTF8");
        assertEquals(expectedString, outputString);
    }
}