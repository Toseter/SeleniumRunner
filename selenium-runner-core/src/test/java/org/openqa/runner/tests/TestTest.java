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
import org.openqa.selenium.remote.SessionId;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 18.06.11
 * To change this template use File | Settings | File Templates.
 */
public class TestTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TestTest.class);
    }

    private org.openqa.runner.tests.Test test;
    private SessionId sessionId = new SessionId("");
    private Command exCommand;

    @Before
    public void setUp() {
        test = new org.openqa.runner.tests.Test();
        Map<String, String> params = new HashMap<String, String>();
        params.put("url", "/");
        exCommand = new Command("open", params);
        test.addCommand(exCommand);
        test.addCommand(exCommand);
    }

    @After
    public void tearDown() {
        test = null;
    }

    @Test
    public void testHasNext() {
        assertTrue(test.hasNextCommand());
        test.nextCommand();
        assertTrue(test.hasNextCommand());
        test.nextCommand();
        assertFalse(test.hasNextCommand());
    }

    @Test
    public void testNextCommand() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("url", "/index");
        Command command = new Command("open", params);
        Command[] commands = {command, exCommand};
        Fixture fixture = new Fixture(commands);
        fixture.setBaseUrl("fixture");

        org.openqa.runner.tests.Test test = new org.openqa.runner.tests.Test();
        test.addCommand(exCommand);
        test.addCommand(exCommand);
        test.setBaseUrl("test");

        test.setBeforeSuite(fixture);
        test.setAfterSuite(fixture);
        test.setAfterTest(fixture);
        test.setBeforeTest(fixture);

        Command currentCommand = test.nextCommand();

        /* Before Suite */
        assertEquals(command, currentCommand);
        assertEquals("fixture", test.getState().getBaseUrl());

        currentCommand = test.nextCommand();
        assertEquals(exCommand, currentCommand);

        /*Before test */
        currentCommand = test.nextCommand();
        assertEquals(command, currentCommand);
        test.nextCommand();

        /*Test*/
        currentCommand = test.nextCommand();
        assertEquals(exCommand, currentCommand);
        assertEquals("test", test.getState().getBaseUrl());

        currentCommand = test.nextCommand();
        assertEquals(exCommand, currentCommand);


        /* After test */
        currentCommand = test.nextCommand();
        assertEquals(command, currentCommand);
        assertEquals("fixture", test.getState().getBaseUrl());

        currentCommand = test.nextCommand();
        assertEquals(exCommand, currentCommand);
    }
}