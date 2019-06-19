package itsgjk.juba.exceptions;

public class RateLimitException extends RuntimeException {

    private long retryAfter;

    public RateLimitException(String message, long retryAfter){
        super(message);
        this.retryAfter = retryAfter;
    }

    /**
     * Number of milliseconds until this can be executed again
     *
     * @return long of milliseconds, see <a href="https://unbelievable.pizza/api/docs">https://unbelievable.pizza/api/docs</a>
     */
    public long getRetryAfter() {
        return retryAfter;
    }
}
