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


/**
 * Class that contains constant exit codes.
 */
public class ExitCodes {

    /**
     * No such option in config. Appears when parsing config.
     */
    public static final int NO_SUCH_OPTION = 1;
    /**
     * Option must be numerical. Appears when parsing config
     */
    public static final int OPTION_MUST_BE_NUM = 2;
    /**
     * Param expected. Appears when parsing config.
     */
    public static final int PARAM_EXPECTED = 3;

    /**
     * Suite name parameter expected. Appears in parsing config.
     */
    public static final int SUITE_NAME_EXPECTED = 4;

}
