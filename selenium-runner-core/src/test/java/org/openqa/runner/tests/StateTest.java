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
 * Date: 19.07.11
 */
public class StateTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(StateTest.class);
    }

    private State state;
    private Command firstCommand, secondCommand;

    @Before
    public void setUp() {
        state = new State();
        state.setVariable("else", "something");
        Map<String, String> params = new HashMap<String, String>();
        params.put("url", "/");
        firstCommand = new Command("open", params);
        secondCommand = new Command("something", params);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSetGetValue() {
        assertEquals(null, state.getVariable("something"));
        state.setVariable("something", "else");
        assertEquals("else", state.getVariable("something"));
    }

    @Test
    public void testProcessCommands() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("url", "${something}");
        params.put("target", "${else}");
        params = state.processParams(params);
        assertEquals(null, params.get("url"));
        assertEquals("something", params.get("target"));
    }

    @Test
    public void testSetLastCommand() {
        state.setLastCommand(firstCommand);
        state.setLastCommand(secondCommand);

        Iterator<Command> iterator = state.getCallStackIterator();

        assertEquals(firstCommand, iterator.next());
        assertEquals(secondCommand, iterator.next());
        assertEquals(false, iterator.hasNext());
    }

    @Test
    public void testSetIsFailed() {
        state.setLastCommand(firstCommand);
        state.setFailed();
        state.setLastCommand(secondCommand);
        state.setFailed();

        List<TestFail> testFails = state.getTestFails();
        Iterator<Command> iterator = testFails.get(0).getCallStackIterator();

        assertEquals(2, testFails.size());

        assertEquals(firstCommand, iterator.next());
        assertEquals(false, iterator.hasNext());

        iterator = testFails.get(1).getCallStackIterator();
        assertEquals(firstCommand, iterator.next());
        assertEquals(secondCommand, iterator.next());
        assertEquals(false, iterator.hasNext());
    }

    @Test
    public void testSetIsAborted() {
        state.setLastCommand(firstCommand);
        state.setAborted();

        List<TestFail> testFails = state.getTestFails();
        Iterator<Command> iterator = testFails.get(0).getCallStackIterator();

        assertEquals(1, testFails.size());

        assertEquals(firstCommand, iterator.next());
        assertEquals(false, iterator.hasNext());
    }


}