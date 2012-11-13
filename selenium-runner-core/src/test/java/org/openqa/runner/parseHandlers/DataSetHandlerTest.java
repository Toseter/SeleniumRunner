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
import org.openqa.runner.parserHandlers.DataSetHandler;
import org.openqa.runner.tests.DataSet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DataSetHandlerTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DataSetHandlerTest.class);
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHandle() throws Exception {

        URL fileToTestMaterial =  this.getClass().getClassLoader().getResource("dataSet.ds");
        final String testFile = fileToTestMaterial.getFile();
        final int dataSetsCount = 2;


        SAXParserFactory spf = SAXParserFactory.newInstance();

        DataSetHandler handler = new DataSetHandler();

        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(testFile, handler);

        List<DataSet> dataSets = handler.getDataSets();

        assertEquals(dataSetsCount, dataSets.size());

        DataSet dataSet = dataSets.get(0);

        assertEquals("some value", dataSet.getValue("name"));
        assertEquals("some value", dataSet.getValue("other_name"));
    }
}