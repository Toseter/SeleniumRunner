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
import org.openqa.runner.parserHandlers.DataSetHandler;
import org.openqa.runner.parserHandlers.TestHandler;
import org.openqa.runner.parserHandlers.TestSuiteHandler;
import org.openqa.runner.tests.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper that provides parsing of Test,Suites,DataSets,Fixtures.<br/>
 * For parsing SAX is used.All content handlers can be found in {@link org.openqa.runner.parserHandlers}<br/>
 * Usually only <b>{@link #parseTestSuite(String) parseTestSuite}</b> invokes from outside of the class.
 *
 * @see TestHandler
 * @see DataSetHandler
 * @see TestSuiteHandler
 * @see Test
 * @see Fixture
 * @see Suite
 * @see DataSet
 */
public class ParserHelper {

    private static SAXParserFactory spf;

    /**
     * Parse file from path<br/>
     * Workflow:
     * <ol>
     * <li>Parse test</li>
     * <li>If test has fixture, parse fixture</li>
     * <li>If test has dataSets, create clone for each dataSet</li>
     * </ol>
     *
     * @param path path of the file
     * @return list of Test
     * @throws IOException
     * @throws SAXException
     */
    public static List<Test> parseTest(String path) throws IOException, SAXException {

        File testFile = checkFile(path);

        TestHandler handler = new TestHandler();

        if (spf == null)
            spf = SAXParserFactory.newInstance();

        List<Test> tests = new ArrayList<Test>();
        Test result = new Test();

        try {
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(testFile, handler);
            Command[] commands = handler.getCommands();
            result.setBaseUrl(handler.getBaseUrl());
            State state = result.getState();
            state.setTestName(handler.getTitle());
            for (int i = 0; i < commands.length; i++)
                result.addCommand(commands[i]);

            if (handler.getBeforeTest() != null) {
                Fixture beforeTest = parseFixture(testFile.getParent() + File.separator + handler.getBeforeTest());
                result.setBeforeTest(beforeTest);
            }

            if (handler.getAfterTest() != null) {
                Fixture afterTest = parseFixture(testFile.getParent() + File.separator + handler.getAfterTest());
                result.setAfterTest(afterTest);
            }

            if (handler.getDataSet() != null) {
                Test clone;
                List<DataSet> dataSets = parseDataSet(testFile.getParent() + File.separator + handler.getDataSet());
                for (DataSet dataSet : dataSets) {
                    try {
                        clone = (Test) result.clone();
                        clone.getState().setDataSet(dataSet);
                        tests.add(clone);
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(ParserHelper.class).error("Error when cloning test", ex);
                    }
                }
            } else {
                tests.add(result);
            }

        } catch (ParserConfigurationException t) {
            Logger.getLogger(ParserHelper.class).error("Error in parsingTest", t);
        }

        return tests;
    }

    /**
     * Parse Suite file. <br/>
     * Workflow:
     * <ol>
     * <li>Parse Suite File</li>
     * <li>Parse tests for this suite</li>
     * <li>If suite fas fixture, parse fixture</li>
     * </ol>
     *
     * @param path path of the suite
     * @return Suite with Tests
     * @throws IOException
     * @throws SAXException
     */
    public static Suite parseTestSuite(String path) throws IOException, SAXException {

        File testSuiteFile = checkFile(path);

        TestSuiteHandler handler = new TestSuiteHandler();

        if (spf == null)
            spf = SAXParserFactory.newInstance();

        Suite result = null;

        try {
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(testSuiteFile, handler);
            String[] commands = handler.getTests();
            List<Test> tests = new ArrayList<Test>();
            for (int i = 0; i < commands.length; i++)
                tests.addAll(parseTest(testSuiteFile.getParent() + File.separator + commands[i]));


            result = new Suite(tests.toArray(new Test[tests.size()]), handler.getTitle());

            if (handler.getBeforeSuite() != null) {
                Fixture beforeSuite = parseFixture(testSuiteFile.getParent() + File.separator + handler.getBeforeSuite());
                result.setBeforeSuite(beforeSuite);
            }

            if (handler.getAfterSuite() != null) {
                Fixture afterSuite = parseFixture(testSuiteFile.getParent() + File.separator + handler.getAfterSuite());
                result.setAfterSuite(afterSuite);
            }

        } catch (ParserConfigurationException t) {
            Logger.getLogger(ParserHelper.class).error("Error in parseTestSuite", t);
        }

        return result;
    }

    /**
     * Parse dataSet file</br>
     * Each file can contains several dataSets.
     *
     * @param path path to dataSet file
     * @return List of dataSets
     * @throws IOException
     * @throws SAXException
     */
    public static List<DataSet> parseDataSet(String path) throws IOException, SAXException {

        File dataSetFile = checkFile(path);
        DataSetHandler handler = new DataSetHandler();

        if (spf == null)
            spf = SAXParserFactory.newInstance();

        List<DataSet> result = null;

        try {
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(dataSetFile, handler);
            result = handler.getDataSets();

        } catch (ParserConfigurationException t) {
            Logger.getLogger(ParserHelper.class).error("Error in parseTestSuite", t);
        }

        return result;
    }

    /**
     * @param path
     * @return
     * @throws IOException
     * @throws SAXException
     */
    public static Fixture parseFixture(String path) throws IOException, SAXException {

        Fixture fixture = null;

        File testFile = checkFile(path);

        TestHandler handler = new TestHandler();

        if (spf == null)
            spf = SAXParserFactory.newInstance();

        try {

            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(testFile, handler);
            Command[] commands = handler.getCommands();
            fixture = new Fixture(commands);
            fixture.setBaseUrl(handler.getBaseUrl());

        } catch (ParserConfigurationException t) {
            Logger.getLogger(ParserHelper.class).error("Error in parsingTest", t);
        }

        return fixture;
    }

    /**
     * Check file for availability.<br/>
     * if file available returns file object.
     *
     * @param path path to file
     * @return
     * @throws IOException
     */
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