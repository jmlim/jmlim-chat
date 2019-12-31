package io.jmlim.chat.config.auth;

public class UnAuthorizedException extends RuntimeException {
    private String message;

    public UnAuthorizedException(String s) {
        super(s);
        this.message = s;
    }
}
