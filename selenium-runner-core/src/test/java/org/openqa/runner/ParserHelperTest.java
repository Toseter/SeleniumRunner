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
import org.openqa.runner.tests.DataSet;
import org.openqa.runner.tests.Fixture;
import org.openqa.runner.tests.Suite;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ParserHelperTest {

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ParserHelperTest.class);
    }

    String basePath = "";

    @Before
    public void setUp() throws IOException, URISyntaxException {

        URL fileToTestMaterial =  this.getClass().getClassLoader().getResource("firstTest.t");
        File testFile = new File(fileToTestMaterial.toURI());
        basePath = (new File(testFile.getParent())).getAbsolutePath();

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
        List<org.openqa.runner.tests.Test> tests = ParserHelper.parseTest(basePath + File.separator + "firstTest.t");
        org.openqa.runner.tests.Test test = tests.get(0);
        assertTrue(test.hasNextCommand());
        Command command = test.nextCommand();
        assertEquals("open", command.getCommandText());
        Map<String, String> params = command.getParams();
        assertEquals("/", params.get("url"));

        test.nextCommand();
        test.nextCommand();
        command.getCommandText();

        assertEquals("open", command.getCommandText());
        params = command.getParams();
        assertEquals("/", params.get("url"));

        assertEquals("firstTest", test.getState().getTestName());

    }

    @Test
    public void testParseTestSuite() throws Exception {
        Suite testSuite = ParserHelper.parseTestSuite(basePath + File.separator + "testSuite.ts");
        org.openqa.runner.tests.Test[] tests = testSuite.getTests();
        assertEquals(3, tests.length);
    }

    @Test
    public void testParseFixture() throws Exception {
        Fixture fixture = ParserHelper.parseFixture(basePath + File.separator + "fixture.f");
        Command[] commands = fixture.getCommands();
        assertEquals(3, commands.length);
        assertEquals("open", commands[0].getCommandText());
        assertEquals("http://www.google.ru/", fixture.getBaseUrl());
    }


    @Test
    public void testParseData() throws Exception {
        List<DataSet> dataSets = ParserHelper.parseDataSet(basePath + File.separator + "dataSet.ds");

        assertEquals(2, dataSets.size());

        DataSet ds = dataSets.get(0);
        assertEquals("first data set", ds.getName());
        assertEquals("some value", ds.getValue("name"));
        assertEquals("some value", ds.getValue("other_name"));

        ds = dataSets.get(1);
        assertEquals("second data set", ds.getName());
        assertEquals("some value", ds.getValue("name"));
        assertEquals("some value", ds.getValue("other_name"));


    }


}
