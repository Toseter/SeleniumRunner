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
import org.openqa.runner.tests.State;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 03.07.11
 */
public class Commands {

    public static void open(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        try {
            remoteWebDriver.getCommandExecutor().execute(new Command(remoteWebDriver.getSessionId(), DriverCommand.GET, params));
        } catch (Exception ex) {
            Logger.getLogger(CommandMappings.class).error("Error in open", ex);
        }
    }


    /* Assert methods */

    public static void assertText(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertTextPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertTitle(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertValue(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertTable(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertElementPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* Verify Methods */

    public static void verifyText(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyTextPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyTitle(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyValue(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyTable(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyElementPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* WaitFor Methods  */
    public static void waitForText(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForTextPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForTitle(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForValue(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForTable(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForElementPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* Store Methods  */
    public static void storeText(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        String value = remoteWebDriver.findElement(CommandMappings.detectTargetMethod(params.get("target"))).getText();
        state.setVariable(params.get("text"), value);
    }

    public static void storeTextPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void storeTitle(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        state.setVariable(params.get("title"), remoteWebDriver.getTitle());
    }

    public static void storeValue(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        String value = remoteWebDriver.findElement(CommandMappings.detectTargetMethod(params.get("target"))).getAttribute("value");
        state.setVariable(params.get("value"), value);
    }

    public static void storeTable(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void storeElementPresent(RemoteWebDriver remoteWebDriver, State state, Map<String, String> params) {
        throw new NotImplementedException();
    }
}
