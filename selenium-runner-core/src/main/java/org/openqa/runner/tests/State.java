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
 * Contains State of {@link Test}.<br/>
 * Process variables, register fails, stack trace.
 */
public class State implements isCloneable {

    /**
     * Base url
     */
    private String baseUrl;

    /**
     * Name of Test , need for reports
     */
    private String testName = "anonymous";

    private boolean _isCloneable = true;

    /**
     * Don't cause test abortion, for verify
     */
    private boolean isFailed = false;
    /**
     * Fails Test and abort execution
     */
    private boolean isAborted = false;
    /**
     * Current call stack
     */
    private Queue<Command> callStack;

    private int callStackSize;
    private Command lastCommand;

    /**
     * Test fails
     */
    private List<TestFail> testFails;


    public boolean isFailed() {
        return isFailed;
    }


    /**
     * Variables
     */
    private DataSet dataSet;

    public State() {
        baseUrl = "http://localhost";
        dataSet = new DataSet();
        callStack = new LinkedList<Command>();
        testFails = new LinkedList<TestFail>();
        callStackSize = (Integer) Config.getConfig().get("state.callStack.size");
    }

    /**
     * Write fail info into testFails
     */
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

    /**
     * @return List of {@link TestFail}
     */
    public List<TestFail> getTestFails() {
        return testFails;
    }

    /**
     * Register fail(verify failed) of {@link Test}<br/>
     * Don't cause {@link Test} execution abort.
     */
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

    /**
     * Abort {@link Test} execution , and register fail.
     *
     * @TODO add exception when adding new command
     */
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

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
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
        clone.testName = testName;
        clone.dataSet = (DataSet) dataSet.clone();
        return clone;
    }
}