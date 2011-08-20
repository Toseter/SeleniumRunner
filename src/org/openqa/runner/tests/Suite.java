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
 * Test Suite.<br/>
 * Contains Test collection and Suite fixtures.
 *
 * @see Test
 * @see Fixture
 */
public class Suite {

    private Fixture _beforeSuite = null;
    private Fixture _afterSuite = null;

    private String name = "anonymous";

    private Test[] _tests;


    public Suite(Test[] tests) {
        _tests = tests;
    }

    public Suite(Test[] tests, String name) {
        this(tests);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Test[] getTests() {
        return _tests;
    }

    public void setBeforeSuite(Fixture beforeSuite) {
        _beforeSuite = beforeSuite;
        for (Test test : _tests) {
            test.setBeforeSuite(_beforeSuite);
        }
    }

    public void setAfterSuite(Fixture afterSuite) {
        _afterSuite = afterSuite;
        for (Test test : _tests) {
            test.setAfterSuite(_afterSuite);
        }
    }
}
