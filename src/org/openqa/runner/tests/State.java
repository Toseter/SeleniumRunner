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


import org.openqa.runner.Config;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 30.06.11
 */
public class State implements isCloneable {


    private String baseUrl;
    private String testName = "anonymous";


    private boolean _isCloneable = true;

    /*
        Don't cause test abortion, for verify
     */
    private boolean isFailed = false;
    private boolean isAborted = false;
    private Queue<Command> callStack;
    private int callStackSize;
    private Command lastCommand;

    private List<TestFail> testFails;


    public boolean isFailed() {
        return isFailed;
    }


    private DataSet dataSet;

    public State() {
        baseUrl = "http://localhost";
        dataSet = new DataSet();
        callStack = new LinkedList<Command>();
        testFails = new LinkedList<TestFail>();
        callStackSize = (Integer) Config.getConfig().get("state.callStack.size");
    }

    protected void writeFail() {
        TestFail testFail = new TestFail(testName, callStack);
        testFails.add(testFail);
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<TestFail> getTestFails() {
        return testFails;
    }

    public void setFailed() {
        isFailed = true;
        writeFail();
    }

    public Command getLastCommand() {
        return lastCommand;
    }


    public boolean isAborted() {
        return isAborted;
    }

    /* @TODO add exception when adding new command */
    public void setAborted() {
        isAborted = true;
        writeFail();
    }

    public void setLastCommand(Command lastCommand) {
        if (callStack.size() == callStackSize)
            callStack.poll();

        callStack.add(lastCommand);
        this.lastCommand = lastCommand;
    }

    /*
     * Use this method instead of returning queue, because
     * implementation could change
     */
    public Iterator<Command> getCallStackIterator() {
        return callStack.iterator();
    }

    public void setVariable(String name, String value) {
        dataSet.setValue(name, value);
    }

    public String getVariable(String name) {
        return dataSet.getValue(name);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map<String, String> processParams(Map<String, String> params) {
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            if (value.matches("\\$\\{\\w*\\}")) {
                value = value.replace("${", "").replace("}", "");
                params.put(key, this.getVariable(value));
            }
        }

        return params;
    }

    @Override
    public boolean isCloneable() {
        return _isCloneable;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        if (!isCloneable()) {
            throw new CloneNotSupportedException("Not cloneable at this state.");
        }

        State clone = new State();
        clone.baseUrl = baseUrl;
        clone.dataSet = (DataSet) dataSet.clone();
        return clone;
    }
}
