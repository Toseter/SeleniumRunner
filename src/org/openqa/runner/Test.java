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

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 09.06.11
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    /**
     * @HACK JUnit uses different thread, so this hack prevents ConcurrentModificationException
     * What can I do with this?
     */
    protected ConcurrentLinkedQueue<Map<String, Map<String, String>>> _commands = new ConcurrentLinkedQueue<Map<String, Map<String, String>>>();

    public Test() {
        super();
    }

    public Map<String, Map<String, String>> nextCommand() {
        return _commands.poll();
    }

    public boolean hasNextCommand() {
        return !(_commands.isEmpty());
    }

    public void addCommand(Map<String, Map<String, String>> c) {
        _commands.add(c);
    }

}
