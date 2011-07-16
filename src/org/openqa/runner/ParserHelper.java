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


import org.apache.log4j.Logger;
import org.openqa.runner.parserHandlers.TestHandler;
import org.openqa.runner.parserHandlers.TestSuiteHandler;
import org.xml.sax.SAXException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 09.06.11
 * To change this template use File | Settings | File Templates.
 */
public class ParserHelper {

    private static SAXParserFactory spf;

    /**
     * Parse Test from file
     *
     * @param path String
     * @return Test object
     */
    public static Test parseTest(String path) throws IOException, SAXException {

        File testFile = checkFile(path);

        TestHandler handler = new TestHandler();

        if (spf == null)
            spf = SAXParserFactory.newInstance();

        Test result = new Test();

        try {
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(testFile, handler);
            Object[] commands = handler.getCommands();
            result.getState().setBaseUrl(new URL(handler.getBaseUrl()));
            for (int i = 0; i < commands.length; i++)
                result.addCommand((Map<String, Map<String, String>>) commands[i]);
        } catch (ParserConfigurationException t) {
            Logger.getLogger(ParserHelper.class).error("Error in parsingTest", t);
        }

        return result;
    }

    public static TestSuite parseTestSuite(String path) throws IOException, SAXException {

        File testSuiteFile = checkFile(path);

        TestSuiteHandler handler = new TestSuiteHandler();

        if (spf == null)
            spf = SAXParserFactory.newInstance();

        TestSuite result = null;

        try {
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(testSuiteFile, handler);
            String[] commands = handler.getTests();
            Test[] tests = new Test[commands.length];
            for (int i = 0; i < commands.length; i++)
                tests[i] = parseTest(testSuiteFile.getParent() + File.separator + commands[i]);

            result = new TestSuite(tests);

        } catch (ParserConfigurationException t) {
            Logger.getLogger(ParserHelper.class).error("Error in parseTestSuite", t);
        }

        return result;
    }

    public static TestData parseData() {
        throw new NotImplementedException();
    }

    public static Fixture parseFixture() {
        throw new NotImplementedException();
    }

    private static File checkFile(String path) throws IOException {
        File testFile = new File(path);

        if (!testFile.exists()) {
            throw new FileNotFoundException("Can't find " + path);
        }

        if (!testFile.canRead()) {
            throw new IOException("Can't read file " + path);
        }
        return testFile;
    }

}
