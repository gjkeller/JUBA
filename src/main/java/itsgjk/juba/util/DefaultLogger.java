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

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Code inspiration from SLF4J's SimpleLogger, made to be simple and have just enough features for JUBA.
 * It is recommended to override JUBA's default logger with a different one, such as Log4J2.
 */
public class DefaultLogger extends MarkerIgnoringBase {

    private static boolean INITIALIZED = false;
    private final static LogLevel DEFAULT_LEVEL = LogLevel.INFO;
    private final static boolean SHOW_DATE_TIME = false;
    private final static boolean SHOW_THREAD = true;
    private final static String DATE_FORMAT = "yyyy-mm-dd hh:mm:ss";
    private static DateFormat DATE_FORMATTER;

    public DefaultLogger(String name){
        super.name = name;
        if(!INITIALIZED) init();
    }

    private static void init(){
        INITIALIZED = true;
        if(SHOW_DATE_TIME){
            DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
        }
    }

    private boolean isEnabled(LogLevel level){
        return level.getLevelInt() >= DEFAULT_LEVEL.getLevelInt();
    }

    public void log(LogLevel level, String message){
        log(level, message, null);
    }

    public void log(LogLevel level, String message, Throwable throwable){
        if(!isEnabled(level)) return;

        StringBuilder sb = new StringBuilder();

        if(SHOW_DATE_TIME){
            Date now = new Date();
            String toAppend;
            synchronized (DATE_FORMATTER){
                toAppend = DATE_FORMATTER.format(now);
            }
            sb.append(toAppend);
            sb.append(' ');
        }

        if(SHOW_THREAD){
            sb.append('[');
            sb.append(Thread.currentThread().getName());
            sb.append("] ");
        }

        sb.append(level.getName());
        sb.append(' ');

        sb.append(name).append(" - ");

        sb.append(message);

        level.getPrintStream().println(sb);
        if(throwable != null) throwable.printStackTrace(level.getPrintStream());

        //ensure contents are flushed if autoflush isn't enabled
        level.getPrintStream().flush();
    }

    public void formatAndLog(LogLevel level, String message, Object object1, Object object2){
        if(!isEnabled(level)) return;

        FormattingTuple tp = MessageFormatter.format(message, object1, object2);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    public void formatAndLog(LogLevel level, String message, Object... objects){
        if(!isEnabled(level)) return;

        FormattingTuple tp = MessageFormatter.format(message, objects);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    @Override
    public boolean isTraceEnabled() {
        return isEnabled(LogLevel.TRACE);
    }

    @Override
    public void trace(String s) {
        log(LogLevel.TRACE, s);
    }

    @Override
    public void trace(String s, Object o) {
        formatAndLog(LogLevel.TRACE, s, o, null);
    }

    @Override
    public void trace(String s, Object o1, Object o2) {
        formatAndLog(LogLevel.TRACE, s, o1, o2);
    }

    @Override
    public void trace(String s, Object... objects) {
        formatAndLog(LogLevel.TRACE, s, objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        log(LogLevel.TRACE, s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return isEnabled(LogLevel.DEBUG);
    }

    @Override
    public void debug(String s) {
        log(LogLevel.DEBUG, s);
    }

    @Override
    public void debug(String s, Object o) {
        formatAndLog(LogLevel.DEBUG, s, o, null);
    }

    @Override
    public void debug(String s, Object o1, Object o2) {
        formatAndLog(LogLevel.DEBUG, s, o1, o2);
    }

    @Override
    public void debug(String s, Object... objects) {
        formatAndLog(LogLevel.DEBUG, s, objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        log(LogLevel.DEBUG, s, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return isEnabled(LogLevel.INFO);
    }

    @Override
    public void info(String s) {
        log(LogLevel.INFO, s);
    }

    @Override
    public void info(String s, Object o) {
        formatAndLog(LogLevel.INFO, s, o, null);
    }

    @Override
    public void info(String s, Object o1, Object o2) {
        formatAndLog(LogLevel.INFO, s, o1, o2);
    }

    @Override
    public void info(String s, Object... objects) {
        formatAndLog(LogLevel.INFO, s, objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        log(LogLevel.INFO, s, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return isEnabled(LogLevel.WARN);
    }

    @Override
    public void warn(String s) {
        log(LogLevel.WARN, s);
    }

    @Override
    public void warn(String s, Object o) {
        formatAndLog(LogLevel.WARN, s, o, null);
    }

    @Override
    public void warn(String s, Object... objects) {
        formatAndLog(LogLevel.WARN, s, objects);
    }

    @Override
    public void warn(String s, Object o1, Object o2) {
        formatAndLog(LogLevel.WARN, s, o1, o2);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        log(LogLevel.WARN, s, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return isEnabled(LogLevel.ERROR);
    }

    @Override
    public void error(String s) {
        log(LogLevel.ERROR, s);
    }

    @Override
    public void error(String s, Object o) {
        formatAndLog(LogLevel.ERROR, s, o, null);
    }

    @Override
    public void error(String s, Object o1, Object o2) {
        formatAndLog(LogLevel.ERROR, s, o1, o2);
    }

    @Override
    public void error(String s, Object... objects) {
        formatAndLog(LogLevel.ERROR, s, objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        log(LogLevel.ERROR, s, throwable);
    }
}
