package itsgjk.juba.requests;

import itsgjk.juba.core.JUBA;
import itsgjk.juba.util.Route;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class JUBARestAction<T> {

    private JUBA juba;
    private Route.CompiledRoute route;
    private static Consumer<?> DEFAULT_SUCCESS_CALLBACK = t -> {};
    private static Consumer<? extends Throwable> DEFAULT_ERROR_CALLBACK = t -> {
        JUBA.LOGGER.error("Request returned error:");
        t.printStackTrace();
    };

    public JUBARestAction(JUBA juba, Route.CompiledRoute route){
        this.juba = juba;
        this.route = route;
    }

    @SuppressWarnings("unchecked")
    public void queue(){
        queue((Consumer<T>) DEFAULT_SUCCESS_CALLBACK, DEFAULT_ERROR_CALLBACK);
    }

    public void queue(Consumer<T> success){
        queue(success, DEFAULT_ERROR_CALLBACK);
    }

    public void queue(Consumer<T> success, Consumer<? extends Throwable> error){
        CompletableFuture future = juba.getRequester().getResponseAsync(route)
    }

    public T complete(){
        return
    }
}
