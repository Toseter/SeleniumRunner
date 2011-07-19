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

import org.apache.log4j.Logger;
import org.openqa.runner.CommandMappings;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 20.06.11
 */
public class Executor extends RemoteWebDriver {

    public Executor(CommandExecutor commandExecutor, Capabilities desiredCapabilities) {
        super(commandExecutor, desiredCapabilities);
    }

    public Executor(URL url, Capabilities desiredCapabilities) {
        super(url, desiredCapabilities);
    }

    public SuiteResult execute(Suite testSuite) {
        SuiteResult testSuiteResult = null;

        for (Test test : testSuite.getTests()) {
            while (test.hasNextCommand()) {
                Command command = test.nextCommand();
                String commandText = command.getCommandText();
                State state = test.getState();
                try {
                    CommandMappings.execute(this, state, commandText, command.getParams());
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(Executor.class).error("Fail execute command :" + commandText, ex);
                }
            }
        }

        return testSuiteResult;
    }

}
