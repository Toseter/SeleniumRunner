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

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IntelliJ IDEA.
 * User: lex
 * Date: 09.06.11
 * To change this template use File | Settings | File Templates.
 */
public class Test implements isCloneable {

    private String _baseUrl;
    private State _state;

    private boolean _isCloneable = true;

    private int commandState;

    private int fixtureCounter;

    private Fixture beforeSuite = null;
    private Fixture beforeTest = null;
    private Fixture afterTest = null;
    private Fixture afterSuite = null;

    /**
     * @HACK JUnit uses different thread, so this hack prevents ConcurrentModificationException
     * What can I do with this?
     */
    protected ConcurrentLinkedQueue<Command> _commands = new ConcurrentLinkedQueue<Command>();

    public Test() {
        super();
        _state = new State();
        commandState = 0;
    }


    /*
     * @TODO Refactor nextCommand
     */
    private Command getFromFixture(Fixture fixture) {
        Command result;


        if (fixture == null) {
            commandState++;
            fixtureCounter = 0;
            return null;
        }

        if (fixtureCounter == 0) {
            getState().setBaseUrl(fixture.getBaseUrl());
        }

        if (fixtureCounter == fixture.getCommands().length) {
            commandState++;
            fixtureCounter = 0;
            return null;
        }

        result = fixture.getCommands()[fixtureCounter];
        fixtureCounter++;

        return result;
    }

    public Command nextCommand() {

        _isCloneable = false;

        Command result = null;

        while (result == null) {
            switch (commandState) {
                case 0:
                    result = getFromFixture(beforeSuite);
                    break;
                case 1:
                    result = getFromFixture(beforeTest);
                    break;
                case 2:

                    if (fixtureCounter == 0) {
                        getState().setBaseUrl(_baseUrl);
                    }

                    if (_commands.isEmpty()) {
                        commandState++;
                        break;
                    }

                    result = _commands.poll();

                    break;
                case 3:
                    result = getFromFixture(afterTest);
                    break;
                case 4:
                    result = getFromFixture(afterSuite);
                    break;
            }
        }

        return result;
    }

    public boolean hasNextCommand() {
        return !(_commands.isEmpty());
    }

    public void addCommand(Command command) {
        _commands.add(command);
    }

    public State getState() {
        return _state;
    }

    public String getBaseUrl() {
        return _baseUrl;
    }

    public void setBaseUrl(String _baseUrl) {
        this._baseUrl = _baseUrl;
    }

    public void setBeforeSuite(Fixture beforeSuite) {
        this.beforeSuite = beforeSuite;
    }

    public void setBeforeTest(Fixture beforeTest) {
        this.beforeTest = beforeTest;
    }

    public void setAfterTest(Fixture afterTest) {
        this.afterTest = afterTest;
    }

    public void setAfterSuite(Fixture afterSuite) {
        this.afterSuite = afterSuite;
    }


    public boolean isCloneable() {
        return (_isCloneable && _state.isCloneable());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        if (!isCloneable()) {
            throw new CloneNotSupportedException("Not cloneable at this state.");
        }

        Test clone = new Test();
        clone._state = (State) _state.clone();
        clone._commands = new ConcurrentLinkedQueue<Command>(_commands);
        clone.beforeSuite = beforeSuite;
        clone.afterSuite = afterSuite;
        clone.beforeTest = beforeTest;
        clone.afterTest = afterTest;
        return clone;
    }


}
