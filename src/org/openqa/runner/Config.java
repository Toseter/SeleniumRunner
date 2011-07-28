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

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 20.06.11
 */
public class Config {

    private static Map<String, Object> _config;

    public static void parse(String[] args) {

    }

    public static Map<String, Object> getConfig() {
        if (_config != null)
            return _config;

        defaultConfig();
        return _config;
    }

    private static void defaultConfig() {
        _config = new ImmutableMap.Builder<String, Object>().
                put("rc_url", "http://localhost:4444").
                put("browsers", "*firefox").
                put("callStack.size", 5).
                build();
    }
}
