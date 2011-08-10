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

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 09.06.11
 * To change this template use File | Settings | File Templates.
 */
public class Fixture {

    private String _baseUrl;
    private Command[] _commands;

    public Fixture(Command[] commands) {
        _commands = commands;
    }

    public Command[] getCommands() {
        return _commands;
    }

    public void setBaseUrl(String url) {
        _baseUrl = url;
    }

    public String getBaseUrl() {
        return _baseUrl;
    }
}
