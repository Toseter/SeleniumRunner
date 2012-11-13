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

import java.util.Map;


/**
 * Command class , contains commandText and command params
 *
 * @see Test
 */
public class Command {

    private String commandText;
    private Map<String, String> params;

    public String getCommandText() {
        return commandText;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Command(String commandText, Map<String, String> params) {
        this.commandText = commandText;
        this.params = params;
    }
}
