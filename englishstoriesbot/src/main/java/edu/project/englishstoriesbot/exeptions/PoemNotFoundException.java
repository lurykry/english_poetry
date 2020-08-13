package edu.project.englishstoriesbot.exeptions;

public class PoemNotFoundException extends Exception{
    public PoemNotFoundException() {
    }

    public PoemNotFoundException(String message) {
        super(message);
    }

    public PoemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoemNotFoundException(Throwable cause) {
        super(cause);
    }

    public PoemNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
