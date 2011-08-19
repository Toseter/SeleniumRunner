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

package org.openqa.runner.resultFormaters;

import org.openqa.runner.CommandMappings;
import org.openqa.runner.tests.Command;
import org.openqa.runner.tests.SuiteResult;
import org.openqa.runner.tests.TestFail;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DefaultResultFormater extends ResultFormater {

    private List<SuiteResult> _results;

    public DefaultResultFormater() {
        super();
        _results = new LinkedList<SuiteResult>();
    }

    @Override
    public void AddSuiteResult(SuiteResult suiteResult) {
        _results.add(suiteResult);
    }

    @Override
    public void Process() {
        StringBuilder stringBuilder = new StringBuilder();

        for (SuiteResult result : _results) {
            stringBuilder.append("[" + result.getTestSuiteName() + "]\r\n")
                    .append("Tests executed:").append(result.getDoneTestCount())
                    .append("\r\nTests failed:").append(result.getFailedTestCount()).append("\r\n\r\n");

            for (TestFail fail : result.getTestFails()) {
                stringBuilder.append("{" + fail.getTestName() + "}\r\n")
                        .append(formatCallStack(fail.getCallStackIterator()))
                        .append("\r\n\r\n");

            }
        }

        System.out.println(stringBuilder.toString());
    }

    private String formatCallStack(Iterator<Command> commandsIterator) {
        StringBuilder stringBuilder = new StringBuilder();
        Command command;
        int i = 0;
        Map<String, String> comMap = CommandMappings.getParamMapping();


        while (commandsIterator.hasNext()) {
            command = commandsIterator.next();


            String params = comMap.get(command.getCommandText());
            String[] paramsArr = params.split(":");
            Map<String, String> paramsValue = command.getParams();
            stringBuilder.append(i).append(": <")
                    .append(command.getCommandText())
                    .append("> ");

            for (String p : paramsArr) {
                stringBuilder.append(p).append("='").append(paramsValue.get(p)).append("'\r\n");
            }
            i++;
        }


        return stringBuilder.toString();
    }

}
