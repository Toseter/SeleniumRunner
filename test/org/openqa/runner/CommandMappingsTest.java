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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

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

    /**
     * This test depends on current Selenium Implementation
     *
     * @TODO add ByDom , when finish ByDom class
     */
    @Test
    public void testDetectTargetMethod() {

        String target;

        target = "//some/path";
        By context = CommandMappings.detectTargetMethod(target);
        assertEquals("By.xpath: //some/path", context.toString());

        target = "xpath=//some/path";
        context = CommandMappings.detectTargetMethod(target);
        assertEquals("By.xpath: //some/path", context.toString());

        target = "link=Some-text";
        context = CommandMappings.detectTargetMethod(target);
        assertEquals("By.linkText: Some-text", context.toString());

        target = "css=#some-id";
        context = CommandMappings.detectTargetMethod(target);
        assertEquals("By.selector: #some-id", context.toString());

        target = "name=some-name";
        context = CommandMappings.detectTargetMethod(target);
        assertEquals("By.name: some-name", context.toString());

        target = "id=some-id";
        context = CommandMappings.detectTargetMethod(target);
        assertEquals("By.id: some-id", context.toString());

        target = "some target";
        context = CommandMappings.detectTargetMethod(target);
        assertEquals("by id or name \"some target\"", context.toString());
    }
}