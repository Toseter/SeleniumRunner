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
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 20.06.11
 */
public class TestExecutor extends RemoteWebDriver {

    public TestExecutor(CommandExecutor commandExecutor, Capabilities desiredCapabilities) {
        super(commandExecutor, desiredCapabilities);
    }

    public TestExecutor(URL url, Capabilities desiredCapabilities) {
        super(url, desiredCapabilities);
    }

    public TestSuiteResult execute(TestSuite testSuite) {
        TestSuiteResult testSuiteResult = null;

        for (Test test : testSuite.getTests()) {
            while (test.hasNextCommand()) {
                Map<String, Map<String, String>> command = test.nextCommand();
                String commandText = (String) command.keySet().toArray()[0];
                try {
                    CommandMappings.execute(this, commandText, command.get(commandText));
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(TestExecutor.class).error("Fail execute command :" + commandText, ex);
                }
            }
        }

        return testSuiteResult;
    }

}
