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

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 17.08.11
 */
public class DataSetTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DataSetTest.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSetGet() {

        DataSet dataSet = new DataSet();
        dataSet.setValue("some", "value");

        assertEquals("value", dataSet.getValue("some"));

        dataSet.setValue("some", "other");
        assertEquals("other", dataSet.getValue("some"));


        assertEquals(null, dataSet.getValue("novalue"));


    }
}