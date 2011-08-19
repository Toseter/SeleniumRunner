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

import java.util.LinkedList;
import java.util.List;

public class SuiteResult {

    /* @TODO Think about how write about not failed tests */
    private String testSuiteName;
    private int failedTestCount = 0;
    private int doneTestCount;
    private List<TestFail> testFails;

    public int getFailedTestCount() {
        return failedTestCount;
    }

    public int getDoneTestCount() {
        return doneTestCount;
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }

    public List<TestFail> getTestFails() {
        return testFails;
    }

    public SuiteResult(Suite suite) {
        testFails = new LinkedList<TestFail>();
        testSuiteName = suite.getName();
        Test[] tests = suite.getTests();
        doneTestCount = tests.length;
        for (int i = 0; i < tests.length; i++) {
            State testState = tests[i].getState();

            if (testState.isFailed() || testState.isAborted()) {
                failedTestCount++;
                testFails.addAll(testState.getTestFails());
            }

        }
    }
}
