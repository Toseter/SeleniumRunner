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

package org.openqa.runner.parserHandlers;


import org.openqa.runner.tests.DataSet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

public class DataSetHandler extends DefaultHandler {

    private List<DataSet> dataSets;
    private DataSet currentDataset;

    private int state;

    private String title;
    private String name;
    private String value;

    @Override
    public void startDocument() throws SAXException {
        dataSets = new LinkedList<DataSet>();
        state = -1;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (qName.equals("table")) {
            title = "";
            name = "";
            value = "";
            state = -1;
            currentDataset = new DataSet();
        }

        if (qName.equals("td")) {
            state++;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String newString = new String(ch, start, length);

        switch (state) {
            case 0:
                title = title.concat(newString);
                break;
            case 1:
                name = name.concat(newString);
                break;
            case 2:
                value = value.concat(newString);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("td") && (state == 2)) {
            currentDataset.setValue(name.trim(), value.trim());
            name = "";
            value = "";
            state = 0;
        }

        if (qName.equals("table")) {
            currentDataset.setName(title.trim());
            dataSets.add(currentDataset);
            currentDataset = null;
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }


    public List<DataSet> getDataSets() {
        return dataSets;
    }
}
