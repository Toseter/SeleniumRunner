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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TestFail {

    private String testName;
    private Queue<Command> callStack;

    public TestFail(String testName, Queue<Command> callStack) {
        this.testName = testName;
        this.callStack = new LinkedList<Command>();
        this.callStack.addAll(callStack);
    }

    public String getTestName() {
        return testName;
    }

    public Iterator<Command> getCallStackIterator() {
        return callStack.iterator();
    }
}
