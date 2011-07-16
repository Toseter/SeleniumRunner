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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 05.07.11
 */
public class TestSuiteHandler extends DefaultHandler {

    private int start;
    private String uri = "";
    private ArrayList<String> tests = new ArrayList<String>();

    public String[] getTests() {
        return tests.toArray(new String[tests.size()]);
    }

    @Override
    public void startDocument() {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("tbody".equals(qName))
            start = -1;

        if ("tr".equals(qName)) {
            start++;
            this.uri = "";

        }

        if (("a".equals(qName)) && (start >= 0)) {
            this.uri = attributes.getValue("href");
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (("tr".equals(qName)) && (start > 0)) {
            tests.add(this.uri);
        }


    }

    @Override
    public void endDocument() throws SAXException {
    }
}
