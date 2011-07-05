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

import static org.junit.Assert.*;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 26.06.11
 */
public class CommandMappingsTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CommandMappingsTest.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDetectTargetMethod() {
        String xpath1 = "//test";
        String xpath2 = "xpath=//test";

        assertEquals(CommandMappings.XPATH,CommandMappings.detectTargetMethod(xpath1));
        assertEquals(CommandMappings.XPATH,CommandMappings.detectTargetMethod(xpath2));

        String idnt1 = "test";
        String idnt2 = "identifier=test";

        assertEquals(CommandMappings.IDENTIFIER,CommandMappings.detectTargetMethod(idnt1));
        assertEquals(CommandMappings.IDENTIFIER,CommandMappings.detectTargetMethod(idnt2));

        String id = "id=test";

        assertEquals(CommandMappings.ID,CommandMappings.detectTargetMethod(id));

        String css = "css=#test";

        assertEquals(CommandMappings.CSS,CommandMappings.detectTargetMethod(css));

        String dom1 = "dom=document.";
        String dom2 = "document.forms[0].username";

        assertEquals(CommandMappings.DOM,CommandMappings.detectTargetMethod(dom1));
        assertEquals(CommandMappings.DOM,CommandMappings.detectTargetMethod(dom2));

        String link = "link=test";

        assertEquals(CommandMappings.LINK_TEXT,CommandMappings.detectTargetMethod(link));

        String name = "name=test";

        assertEquals(CommandMappings.NAME, CommandMappings.detectTargetMethod(name));

        String none = "$%test";

        assertEquals(CommandMappings.NONE, CommandMappings.detectTargetMethod(none));
    }
}