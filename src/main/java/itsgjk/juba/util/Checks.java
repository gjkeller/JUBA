/*
 * Copyright 2019 Gabriel Keller
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
