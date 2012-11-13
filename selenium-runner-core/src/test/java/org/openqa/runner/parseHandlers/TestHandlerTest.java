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
import org.openqa.runner.parserHandlers.TestHandler;
import org.openqa.runner.tests.Command;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;


public class TestHandlerTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TestHandlerTest.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHandle() throws Exception {

        URL fileToTestMaterial =  this.getClass().getClassLoader().getResource("firstTest.t");
        final String testFile = fileToTestMaterial.getFile();

        SAXParserFactory spf = SAXParserFactory.newInstance();

        TestHandler handler = new TestHandler();

        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(testFile, handler);


        Command[] commands = handler.getCommands();

        assertEquals(commands.length, 5);
        assertEquals("open", commands[0].getCommandText());
        assertEquals("/", commands[0].getParams().get("url"));

        int n = commands.length - 1;
        assertEquals("assertTextPresent", commands[n].getCommandText());
        assertEquals("Begin: write and run tests in Firefox", commands[n].getParams().get("text"));

        assertEquals("http://www.google.ru/", handler.getBaseUrl());
        assertEquals("fixture.f", handler.getBeforeTest());
        assertEquals("fixture.f", handler.getAfterTest());
        assertEquals("dataSet.ds", handler.getDataSet());
        assertEquals("firstTest", handler.getTitle());

    }
}