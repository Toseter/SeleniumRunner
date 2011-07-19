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


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 30.06.11
 */
public class State {


    private URL baseUrl;


    private Map<String, String> variables;

    public State() {
        try {
            baseUrl = new URL("http://localhost");
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Error in code", ex);
        }

        variables = new HashMap<String, String>();
    }

    public void setVariable(String name, String value) {
        variables.put(name, value);
    }

    public String getVariable(String name) {
        return variables.get(name);
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

}
