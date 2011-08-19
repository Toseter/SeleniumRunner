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

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Config class.<br/>
 * Exercise CLI args parsing , and contains config.<br/>
 * Can print messages in console.<br/>
 * <br/>
 * <p/>
 * Exit codes can be found in {@link ExitCodes}
 *
 * @see CliHelper
 * @see ExitCodes
 */
public class Config {

    private final static String VERSION = "0.0.1";
    private static Map<String, Object> _config;

    private final static int STRING = 1;
    private final static int INT = 2;
    private final static int WO_PARAM = 3;


    /*
     * Mapping for easy change CLI ( just change key )
     * And other purpose avoid config-key collisions
     */
    private static Map<String, String>
            _configMappings = new ImmutableMap.Builder<String, String>()
            .put("rc_url", "executor.rc_url")
            .put("browsers", "executor.browsers")
            .build();


    private static Map<String, Integer>
            _configMappingsTypes = new ImmutableMap.Builder<String, Integer>()
            .put("executor.rc_url", STRING)
            .put("executor.browsers", STRING)
            .build();


    private static boolean _isRunnable = true;

    /**
     * Parse CLI args and set defaultConfiguration</br>
     *
     * @param args command line args
     */
    public static void parseConfiguration(String args[]) {
        hasDefaultConfig();

        boolean isParamValue = false;
        String optionName = "";
        String paramName = "";
        for (String param : args) {

            if (isNotRunnableParam(param))
                return;

            if (param.startsWith("-")) {
                try {
                    optionName = _configMappings.get(param.replace("-", ""));
                } catch (NoSuchElementException ex) {
                    System.err.print("No such option \"" + param + "\"");
                    System.exit(ExitCodes.NO_SUCH_OPTION);
                }

                if (isParamValue == true) {
                    if (_configMappingsTypes.get(optionName) != WO_PARAM) {
                        System.err.println("Missing parameter value for " + paramName);
                        System.exit(ExitCodes.PARAM_EXPECTED);
                    } else {
                        _config.put(optionName, true);
                    }

                }

                isParamValue = true;
            } else {
                if (isParamValue) {
                    Object paramValue = null;

                    paramValue = parseValue(optionName, param);

                    if (paramValue == null) {
                        System.err.println("Parameter " + paramName + " must be a number");
                        System.exit(ExitCodes.OPTION_MUST_BE_NUM);
                    }

                    _config.put(optionName, paramValue);
                } else {
                    _config.put("runner.suite_name", param);
                }
                isParamValue = false;
            }

        }
    }

    /**
     * Property, is Runner must continue execution.
     *
     * @return
     */
    public static boolean isRunnable() {
        return _isRunnable;
    }

    public static Map<String, Object> getConfig() {
        if (_config != null)
            return _config;

        defaultConfig();
        return _config;
    }

    private static Object parseValue(String name, String value) throws NumberFormatException {
        int code = _configMappingsTypes.get(name);

        Object result = null;

        switch (code) {
            case STRING:
                result = value;
                break;
            case INT:
                result = Integer.parseInt(value);
                break;
            default:
                result = null;
        }

        return result;
    }

    private static boolean isNotRunnableParam(String param) {
        if (param.equals("-v") || param.equals("--version")) {
            _isRunnable = false;
            CliHelper.printVersionMessage();
            return true;
        }

        if (param.equals("-h") || param.equals("--help")) {
            _isRunnable = false;
            CliHelper.printHelpMessage();
            return true;
        }

        return false;
    }

    private static void hasDefaultConfig() {
        if (_config == null)
            defaultConfig();
    }

    private static void defaultConfig() {

        _config = new HashMap<String, Object>();
        _config.put("common.version", VERSION);

        /* executor default options */
        _config.put("executor.rc_url", "http://localhost:4444/wd/hub");
        _config.put("executor.browsers", "*firefox");

        /* Test state default options */
        _config.put("state.callStack.size", 5);
    }
}
