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

package itsgjk.juba.requests;

import itsgjk.juba.util.Route;
import okhttp3.Response;
import org.json.JSONObject;

public abstract class JUBAAuditableRestAction<T> extends JUBARestAction<T> {

    public JUBAAuditableRestAction(Requester requester, Route.CompiledRoute route, JSONObject data){
        super(requester, route, data);
    }

    /**
     * Adds a reason to this action for UnbelievaBoat's Audit Logs
     *
     * @param reason String reason for action
     * @return The same object (JUBAAuditableRestAction) to queue/complete
     */
    public JUBAAuditableRestAction<T> reason(String reason){
        //make sure two reason args aren't set (why?)
        if(data.has("reason")){
            data.remove("reason");
        }

        data.append("reason", reason);
        return this;
    }

    protected abstract T handleResponse(Response response);
}
