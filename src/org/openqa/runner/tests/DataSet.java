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

import java.util.HashMap;

public class DataSet implements Cloneable {

    private HashMap<String, String> valueMap;
    private String name = "anonymous";

    public void setValue(String key, String value) {
        valueMap.put(key, value);
    }

    public String getValue(String key) {
        return valueMap.get(key);
    }

    public DataSet() {
        super();
        valueMap = new HashMap<String, String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        DataSet clone = new DataSet();
        clone.name = name.substring(name.length());
        clone.valueMap = new HashMap<String, String>(valueMap);
        return clone;
    }
}
