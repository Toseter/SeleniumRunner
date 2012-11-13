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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 29.07.11
 */
public class SuiteResultTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SuiteResultTest.class);
    }

    private Suite testSuite;
    private Command firstCommand, secondCommand;

    @Before
    public void setUp() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("url", "http://google.com");
        firstCommand = new Command("open", params);
        secondCommand = new Command("something", params);

        org.openqa.runner.tests.Test[] tests = new org.openqa.runner.tests.Test[3];
        for (int i = 0; i < tests.length; i++) {
            tests[i] = new org.openqa.runner.tests.Test();
            tests[i].getState().setLastCommand(firstCommand);
            tests[i].getState().setLastCommand(secondCommand);

        }
        tests[0].getState().setAborted();

        testSuite = new Suite(tests);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        SuiteResult suiteResult = new SuiteResult(testSuite);
        assertEquals(3, suiteResult.getDoneTestCount());
        assertEquals(1, suiteResult.getFailedTestCount());
        List<TestFail> testFails = suiteResult.getTestFails();
        assertEquals(1, testFails.size());

        Iterator<Command> iterator = testFails.get(0).getCallStackIterator();
        assertEquals(firstCommand, iterator.next());
        assertEquals(secondCommand, iterator.next());
        assertEquals(false, iterator.hasNext());
    }


}