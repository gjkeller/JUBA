package itsgjk.juba.requests;

import itsgjk.juba.core.JUBA;
import itsgjk.juba.util.Route;

import java.util.function.Consumer;

public class JUBARequest<T> {

    private JUBA juba;
    private Route route;
    private Consumer<T> successCallback = t -> {};
    private Consumer<? extends Throwable> errorCallback = t -> {
        JUBA.LOGGER.error("Request returned error:");
        t.printStackTrace();
    };

    public JUBARequest(JUBA juba, Route route){
        this.juba = juba;
        this.route = route;
    }

    public
}
