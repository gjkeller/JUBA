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

import itsgjk.juba.core.JUBA;

public class Example {

    public static void main(String[] args){
        JUBA juba = new JUBA(args[0]);
        juba.modifyCashBalance("472264989793583106", "196834326963290112", -1).reason("test").queue(s -> {
            System.out.println("Done! " + s.getCashBalance() + " new cash balance.");
            System.out.println("Rank hasn't been updated, is now " + s.getRank());
        });
    }
}