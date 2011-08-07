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


import org.openqa.runner.resultFormaters.DefaultResultFormater;
import org.openqa.runner.tests.Executor;
import org.openqa.runner.tests.Suite;
import org.openqa.runner.tests.SuiteResult;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Map;

/**
 * Main runner class
 *
 * @TODO make documentation
 */
public class SeleniumRunner {

    public static void main(String[] args) {


        Config.parseConfiguration(args);

        if (Config.isRunnable() == false)
            System.exit(0);

        Map<String, Object> config = Config.getConfig();
        String tsPath = (String) config.get("runner.suite_name");
        if (tsPath == null) {
            System.err.println("You must specify suite name");
            System.exit(ExitCodes.SUITE_NAME_EXPECTED);
        }

        try {
            Suite suite = ParserHelper.parseTestSuite(tsPath);
            Executor executor = new Executor(new URL((String) config.get("executor.rc_url")), DesiredCapabilities.firefox());
            SuiteResult suiteResult = executor.execute(suite);
            DefaultResultFormater defaultResultFormater = new DefaultResultFormater();
            defaultResultFormater.AddSuiteResult(suiteResult);
            defaultResultFormater.Process();
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

}
