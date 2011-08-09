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
import org.junit.Before;
import org.junit.Test;
import org.openqa.runner.tests.Command;
import org.openqa.runner.tests.Fixture;
import org.openqa.runner.tests.Suite;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ParserHelperTest {

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ParserHelperTest.class);
    }


    @Before
    public void setUp() throws IOException {

    }

    @Test(expected = FileNotFoundException.class)
    public void testParseTestThrownFileNotFound() throws IOException, SAXException {
        ParserHelper.parseTest("neverland");
    }

    @Test(expected = IOException.class)
    public void testParseTestThrownIOException() throws IOException, SAXException {

        File fileTest = new File("exTest");

        if (!fileTest.exists())
            fileTest.createNewFile();

        fileTest.setReadable(false);
        fileTest.deleteOnExit();

        ParserHelper.parseTest("exTest");
    }

    @Test
    public void testParseTest() throws Exception {
        org.openqa.runner.tests.Test test = ParserHelper.parseTest("testData" + File.separator + "firstTest.t");
        assertTrue(test.hasNextCommand());
        Command command = test.nextCommand();
        assertEquals("open", command.getCommandText());
        Map<String, String> params = command.getParams();
        assertEquals("/", params.get("url"));
        assertEquals("firstTest", test.getState().getTestName());
    }

    @Test
    public void testParseTestSuite() throws Exception {
        Suite testSuite = ParserHelper.parseTestSuite("testData" + File.separator + "testSuite.ts");
        org.openqa.runner.tests.Test[] tests = testSuite.getTests();
        assertEquals(2, tests.length);
    }

    @Test
    public void testParseFixture() throws Exception {
        Fixture fixture = ParserHelper.parseFixture("testData" + File.separator + "fixture.f");
        Command[] commands = fixture.getCommands();
        assertEquals(3, commands.length);
        assertEquals("open", commands[0].getCommandText());
    }


    @Test
    public void testParseData() throws Exception {

    }


}
