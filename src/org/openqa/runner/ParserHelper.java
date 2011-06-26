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


import org.xml.sax.SAXException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.parsers.SAXParserFactory;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 09.06.11
 * To change this template use File | Settings | File Templates.
 */
public class ParserHelper {

    /**
     * Parse Test from file
     *
     * @param path String
     * @return Test object
     */
    public static Test parseTest(String path) throws IOException, SAXException {

        File testFile = new File(path);

        if (!testFile.exists()) {
            throw new FileNotFoundException("Can't find " + path);
        }

        if (!testFile.canRead()) {
            throw new IOException("Can't read file " + path);
        }


        InputStream is = new FileInputStream(path);

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);


//        try
//        {
//
//        }
//        catch (ParserConfigurationException ex)
//        {
//            Logger.getLogger("org.openqa.runner.ParserHelper").error("Error in configuration",ex);
//        }


        throw new NotImplementedException();
    }

    public static TestSuite parseTestSuite(String path) {
        throw new NotImplementedException();
    }

    public static TestData parseData() {
        throw new NotImplementedException();
    }

    public static Fixture parseFixture() {
        throw new NotImplementedException();
    }

}
