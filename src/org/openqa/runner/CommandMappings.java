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

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 20.06.11
 */
public class CommandMappings {

    private static Map<String, String> _commandMapping, _paramMapping;

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


    public static Map<String, String> getMapping() {
        if (_commandMapping != null)
            return _commandMapping;

        _commandMapping = new ImmutableMap.Builder<String, String>().
                put("open", ":get").
                build();

        return _commandMapping;
    }

    public static Map<String, String> getParamMapping() {

        if (_paramMapping != null)
            return _paramMapping;

        _paramMapping = new ImmutableMap.Builder<String, String>().
                put("open", ":url").
                build();

        return _paramMapping;
    }


}
