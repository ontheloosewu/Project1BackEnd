package dev.wu.exceptions;

public class DuplicateUsernameException extends RuntimeException{

    public DuplicateUsernameException (String message) { super(message); }
}
