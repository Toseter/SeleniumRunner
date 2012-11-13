/*
 * Copyright 2012 Aleksey Shilin
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

package org.openqa.runner.parseHandlers;

import junit.framework.JUnit4TestAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.runner.parserHandlers.TestSuiteHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

import static org.junit.Assert.assertEquals;


public class TestSuiteHandlerTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TestSuiteHandlerTest.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHandle() throws Exception {

        final String testFile = "testData" + File.separator + "testSuite.ts";

        SAXParserFactory spf = SAXParserFactory.newInstance();

        TestSuiteHandler handler = new TestSuiteHandler();

        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(testFile, handler);


        String[] tests = handler.getTests();

        assertEquals(2, tests.length);
        assertEquals("firstTest.t", tests[0]);
        assertEquals("secondTest.t", tests[1]);

        assertEquals("fixture.f", handler.getBeforeSuite());
        assertEquals("fixture.f", handler.getAfterSuite());

        assertEquals("Test Suite", handler.getTitle());

    }
}