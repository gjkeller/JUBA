/*
 * Copyright 2019 Gabriel Keller
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package itsgjk.juba.util;

public class StringUtil {

    public static int countMatches(String input, String contained){
        int index =input.indexOf(contained);
        int count = 0;

        while(index != -1){
            count++;
            input = input.substring(index + 1);
            index = input.indexOf(contained);
        }

        return count;
    }
}
