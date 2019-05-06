/*
 * Copyright 2019 Gabriel Keller
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package itsgjk.juba.core;

import itsgjk.juba.requests.Requester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUBA {

    public static final Logger LOGGER = LoggerFactory.getLogger(JUBA.class);
    private final String token;
    private Requester requester;

    public JUBA(String token){

        if(token==null || token.length() != 147)
            throw new IllegalArgumentException("Provided token was either null or not the valid length (147)");

        this.token = token;
        this.requester = new Requester();
    }

    public Requester getRequester() {
        return requester;
    }
}
