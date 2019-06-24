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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Code inspiration from JDA's <a href="https://github.com/DV8FromTheWorld/JDA/blob/master/src/main/java/net/dv8tion/jda/core/utils/JDALogger.java">JDALogger implementation</a>
 */
public class JUBALogger {

    public static final boolean LOGGER_ENABLED;
    private static final Map<String, Logger> LOGS = new HashMap<>();

    static{
        boolean enabled = false;

        try{
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            enabled = true;
        }
        catch(ClassNotFoundException ex){
            LoggerFactory.getLogger(JUBALogger.class);
        }

        LOGGER_ENABLED =  enabled;
    }

    //prevent creating instance
    private JUBALogger(){}

    public static Logger getLog(String name){
        synchronized (LOGS){
            if(LOGGER_ENABLED)
                return LoggerFactory.getLogger(name);
            else{
                if(LOGS.containsKey(name))
                    return LOGS.get(name);
                else
                    return LOGS.put(name, new DefaultLogger(name));
            }
        }
    }

    public static Logger getLog(Class clazz){
        synchronized (LOGS){
            if(LOGGER_ENABLED)
                return LoggerFactory.getLogger(clazz);
            else{
                if(LOGS.containsKey(clazz.getName()))
                    return LOGS.get(clazz.getName());
                else{
                    Logger l = new DefaultLogger(clazz.getSimpleName());
                    LOGS.put(clazz.getName(), l);
                    return l;
                }
            }
        }
    }
}
