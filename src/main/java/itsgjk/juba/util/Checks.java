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

public class Checks {

    public static void isSnowflake(String id, String name){
        notNull(id, name);
        if(id.length() > 20||!isNumber(id)){
            throw new IllegalArgumentException(name + " is not a valid snowflake (id)");
        }
    }

    public static void check(boolean expression, String message){
        if(!expression)
            throw new IllegalArgumentException(message);
    }

    public static void notNull(Object argument, String name){
        if(argument == null){
            throw new IllegalArgumentException(name + " may not be null");
        }
    }

    public static void notEmpty(String argument, String name){
        notNull(argument, name);

        if(argument.equals(""))
            throw new IllegalArgumentException(name + " may not be empty");
    }

    public static void isNumber(String argument, String name){
        notEmpty(argument, name);

        if(!isNumber(argument))
            throw new IllegalArgumentException(name + " must be a number");
    }

    public static boolean isNumber(String argument){
        for(char c : argument.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }

        return true;
    }
}
