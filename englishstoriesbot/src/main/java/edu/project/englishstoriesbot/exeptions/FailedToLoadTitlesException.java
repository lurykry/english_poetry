package edu.project.englishstoriesbot.exeptions;

public class FailedToLoadTitlesException extends Exception{

    public FailedToLoadTitlesException() {
    }

    public FailedToLoadTitlesException(String message) {
        super(message);
    }

    public FailedToLoadTitlesException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToLoadTitlesException(Throwable cause) {
        super(cause);
    }

    public FailedToLoadTitlesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
