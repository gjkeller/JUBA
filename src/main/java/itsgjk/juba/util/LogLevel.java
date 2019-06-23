/*
 * Copyright 2019 Gabriel Keller
 *
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
 * limitations under the License.
 */

package itsgjk.juba.util;

import java.io.PrintStream;

public enum LogLevel {
    ERROR(4, "ERROR", System.err),
    WARN(3, "WARN", System.err),
    INFO(2, "INFO", System.out),
    DEBUG(1, "DEBUG", System.out),
    TRACE(0, "TRACE", System.out);

    private int levelInt;
    private String name;
    private PrintStream printStream;

    LogLevel(int levelInt, String name, PrintStream printStream){
        this.levelInt = levelInt;
        this.name = name;
        this.printStream = printStream;
    }

    public int getLevelInt() {
        return levelInt;
    }

    public String getName() {
        return name;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
}
