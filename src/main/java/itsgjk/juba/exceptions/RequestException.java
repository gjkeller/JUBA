package itsgjk.juba.exceptions;

public class RequestException extends RuntimeException {

    int code;

    public RequestException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
