package edu.project.englishstoriesbot.exeptions;

public class FailedToLoadPoemException extends Exception{

    public FailedToLoadPoemException() {
    }

    public FailedToLoadPoemException(String message) {
        super(message);
    }

    public FailedToLoadPoemException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToLoadPoemException(Throwable cause) {
        super(cause);
    }

    public FailedToLoadPoemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
