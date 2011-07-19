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

    @Before
    public void setUp() {
        state = new State();
        state.setVariable("else", "something");
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
}