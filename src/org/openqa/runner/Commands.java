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

    public static void open(RemoteWebDriver remoteWebDriver,TestState state , Map<String, String> params) {
        try {
            remoteWebDriver.getCommandExecutor().execute(new Command(remoteWebDriver.getSessionId(), DriverCommand.GET, params));
        } catch (Exception ex) {
            Logger.getLogger(CommandMappings.class).error("Error in open", ex);
        }
    }


    /* Assert methods */

    public static void assertText(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertTextPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertTitle(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertValue(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertTable(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void assertElementPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* Verify Methods */

    public static void verifyText(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyTextPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyTitle(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyValue(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyTable(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void verifyElementPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* WaitFor Methods  */
    public static void waitForText(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForTextPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForTitle(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForValue(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForTable(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void waitForElementPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* Store Methods  */
    public static void storeText(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void storeTextPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void storeTitle(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void storeValue(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void storeTable(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static void storeElementPresent(RemoteWebDriver remoteWebDriver, TestState state, Map<String, String> params) {
        throw new NotImplementedException();
    }
}
