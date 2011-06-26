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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 19.06.11
 */
public class TestHandler extends DefaultHandler {

    private boolean isTitle;
    private int command = -2;

    private String commandText;
    private String paramText;
    private String targetText;

    private String title = "";
    private Map<String, Map<String, ?>> commands;

    @Override
    public void startDocument() throws SAXException {
        commands = new HashMap<String, Map<String, ?>>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        isTitle = "title".equals(qName);

        if ("tbody".equals(qName))
            command = 0;

        if (("tr".equals(qName)) && (command != -2)) {
            command = -1;
            commandText = "";
            paramText = "";
            targetText = "";
        }

        if (("td".equals(qName)) && (command == -1))
            command++;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String newString = new String(ch, start, length);

        if (isTitle)
            title = title.concat(newString);

        switch (command) {
            case 0:
                commandText = commandText.concat(newString);
                break;
            case 1:
                targetText = targetText.concat(newString);
                break;
            case 2:
                paramText = paramText.concat(newString);
                break;

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        isTitle = "title".equals(qName);

        if ("tbody".equals(qName))
            command = -2;


        if (("tr".equals(qName)) && (command != -2)) {

        }
    }

    @Override
    public void endDocument() throws SAXException {
    }


}
