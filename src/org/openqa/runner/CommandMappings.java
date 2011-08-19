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
 * Implements mapping of SeRC commands to {@link Commands} class methods.<br/>
 * Contains signature of SeRC commands.<br/>
 * Execute commands using {@link Commands} class.
 *
 * @see Commands
 */
public class CommandMappings {

    private static Map<String, String> _paramMapping;


    /**
     * Invokes method from {@link Commands} class.
     *
     * @TODO Work at exceptions
     * @see Commands
     * @see org.openqa.runner.tests.Executor
     * @see State
     * @see org.openqa.runner.tests.Command
     */
    public static void execute(RemoteWebDriver remoteWebDriver, State state, String commandText, Map<String, String> params) throws NoSuchMethodException {
        Method method = Commands.class.getMethod(commandText, new Class[]{RemoteWebDriver.class, State.class, Map.class});

        try {
            method.invoke(null, new Object[]{remoteWebDriver, state, params});
        } catch (Exception ex) {
            Logger.getLogger(CommandMappings.class).error("Error when invoke method", ex);
        }
    }

    /**
     * Detect target locator method
     *
     * @param target
     * @return
     * @see By
     * @see RemoteWebDriver#findElement(org.openqa.selenium.By)
     */
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

    /**
     * Returns param mapping
     *
     * @return Param mapping, that represents how command maps with own params
     */
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
