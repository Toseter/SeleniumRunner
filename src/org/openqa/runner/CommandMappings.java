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
import org.openqa.runner.tests.State;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ByDOM;
import org.openqa.selenium.support.ByIdOrName;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 20.06.11
 */
public class CommandMappings {

    private static Map<String, String> _paramMapping;

    public static final int XPATH = 0;
    public static final int ID = 1;
    public static final int LINK_TEXT = 2;
    public static final int CSS = 3;
    public static final int NAME = 4;
    public static final int DOM = 5;
    public static final int IDENTIFIER = 6;
    public static final int NONE = 999;

    /*
    *
    * @TODO Work at exceptions
    *
    * */
    public static void execute(RemoteWebDriver remoteWebDriver, State state, String commandText, Map<String, String> params) throws NoSuchMethodException {
        Method method = Commands.class.getMethod(commandText, new Class[]{RemoteWebDriver.class, State.class, Map.class});

        try {
            method.invoke(null, new Object[]{remoteWebDriver, state, params});
        } catch (Exception ex) {
            Logger.getLogger(CommandMappings.class).error("Error when invoke method", ex);
        }
    }

    public static By detectTargetMethod(String target) {
        if ((target.startsWith("//")) || (target.startsWith("xpath="))) {
            target = target.replace("xpath=", "");
            return By.xpath(target);
        }

        if (target.startsWith("link=")) {
            target = target.replace("link=", "");
            return By.linkText(target);
        }

        if (target.startsWith("css=")) {
            target = target.replace("css=", "");
            return By.cssSelector(target);
        }

        if (target.startsWith("name=")) {
            target = target.replace("name=", "");
            return By.name(target);
        }

        if ((target.startsWith("dom")) || (target.startsWith("document."))) {
            return new ByDOM(target);
        }

        if (target.startsWith("id=")) {
            target = target.replace("id=", "");
            return By.id(target);
        }

        return new ByIdOrName(target);
    }


    public static Map<String, String> getParamMapping() {

        if (_paramMapping != null)
            return _paramMapping;

        _paramMapping = new ImmutableMap.Builder<String, String>().
                put("open", "url").
                put("click", "target").
                put("type", "target:text").
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
