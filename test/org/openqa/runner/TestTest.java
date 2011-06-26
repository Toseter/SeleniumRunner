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

import com.google.common.collect.ImmutableMap;
import junit.framework.JUnit4TestAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.SessionId;

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

    private org.openqa.runner.Test test;
    private SessionId sessionId = new SessionId("");
    private Command exCommand = new Command(sessionId, DriverCommand.GET, ImmutableMap.of("url", "http://yandex.ru"));

    @Before
    public void setUp() {
        test = new org.openqa.runner.Test();
//        test.addCommand(exCommand);
//        test.addCommand(new Command(sessionId,DriverCommand.GET, ImmutableMap.of("url", "http://google.com")));
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
        assertEquals(exCommand, test.nextCommand());
    }
}