package itsgjk.juba.requests;

import itsgjk.juba.core.JUBA;
import itsgjk.juba.util.Route;

import java.util.function.Consumer;

public class JUBARequest<T> {

    JUBARestAction<T> restAction;
    Consumer<T> onSuccess;
    Consumer<? super Throwable> onFailure;
    Route.CompiledRoute compiledRoute;

    public JUBARequest(JUBARestAction<T> restAction, Consumer<T> onSuccess, Consumer<? super Throwable> onFailure, Route.CompiledRoute route) {
        this.restAction = restAction;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
        this.compiledRoute = route;
    }

    public void onSuccess(T successObj){
        try{
            onSuccess.accept(successObj);
        }
        catch(Exception ex){
            JUBA.LOGGER.error("Error occurred when accepting success consumer", ex);
        }
    }

    public void onFailure(Throwable exception){
        try{
            onFailure.accept(exception);
        }
        catch(Exception ex){
            JUBA.LOGGER.error("Error occurred when accepting failure consumer", ex);
        }
    }

    public JUBARestAction<T> getRestAction() {
        return restAction;
    }

    public Route.CompiledRoute getRoute() {
        return compiledRoute;
    }
}
