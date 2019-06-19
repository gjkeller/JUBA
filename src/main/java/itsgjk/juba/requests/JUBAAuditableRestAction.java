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
    public JUBAAuditableRestAction reason(String reason){
        //make sure two reason args aren't set (why?)
        if(data.has("reason")){
            data.remove("reason");
        }

        data.append("reason", reason);
        return this;
    }

    protected abstract T handleResponse(Response response);
}
