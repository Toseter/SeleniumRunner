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

import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 20.06.11
 */
public class CommandMappings {

    private static Map<String, String> _paramMapping;

    public static Response execute(RemoteWebDriver remoteWebDriver, String commandText, Map<String, String> params) throws NoSuchMethodException {
        Method method = CommandMappings.class.getMethod(commandText, new Class[]{RemoteWebDriver.class, Map.class});
        Response response = null;

        try {
            response = (Response) method.invoke(null, new Object[]{remoteWebDriver, params});
        } catch (Exception ex) {
            Logger.getLogger(CommandMappings.class).error("Error when invoke method", ex);
        }

        return response;
    }

    public static Response open(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        try {
            return remoteWebDriver.getCommandExecutor().execute(new Command(remoteWebDriver.getSessionId(), DriverCommand.GET, params));
        } catch (Exception ex) {
            Logger.getLogger(CommandMappings.class).error("Error in open", ex);
        }

        return null;
    }

    /* Assert methods */

    public static Response assertText(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response assertTextPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response assertTitle(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response assertValue(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response assertTable(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response assertElementPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* Verify Methods */

    public static Response verifyText(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response verifyTextPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response verifyTitle(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response verifyValue(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response verifyTable(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response verifyElementPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* WaitFor Methods  */
    public static Response waitForText(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response waitForTextPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response waitForTitle(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response waitForValue(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response waitForTable(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response waitForElementPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    /* Store Methods  */
    public static Response storeText(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response storeTextPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response storeTitle(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response storeValue(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response storeTable(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Response storeElementPresent(RemoteWebDriver remoteWebDriver, Map<String, String> params) {
        throw new NotImplementedException();
    }

    public static Map<String, String> getParamMapping() {

        if (_paramMapping != null)
            return _paramMapping;

        _paramMapping = new ImmutableMap.Builder<String, String>().
                put("open", "url").
                put("assertText", "target:text").
                put("assertTextPresent", "text").
                put("assertTitle", "title").
                put("assertValue", "target:value").
                put("assertTable", "target:value").
                put("assertElementPresent", "target").
                put("verifyText", "target:text").
                put("verifyTextPresent", "text").
                put("verifyTitle", "title").
                put("verifyValue", "target:value").
                put("verifyTable", "target:value").
                put("verifyElementPresent", "target").
                put("waitForText", "target:text").
                put("waitForTextPresent", "text").
                put("waitForTitle", "title").
                put("waitForValue", "target:value").
                put("waitForTable", "target:value").
                put("waitForElementPresent", "target").
                put("storeText", "target:text").
                put("storeTextPresent", "text").
                put("storeTitle", "title").
                put("storeValue", "target:value").
                put("storeTable", "target:value").
                put("storeElementPresent", "target").
                build();

        return _paramMapping;
    }


}
