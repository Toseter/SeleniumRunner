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


public class CliHelper {

    public static void printHelpMessage() {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Sample help message");
        System.out.println(helpMessage.toString());
    }

    public static void printVersionMessage() {
        StringBuilder versionMessage = new StringBuilder();
        versionMessage.append("Sample version info");
        System.out.println(versionMessage.toString());
    }

}
