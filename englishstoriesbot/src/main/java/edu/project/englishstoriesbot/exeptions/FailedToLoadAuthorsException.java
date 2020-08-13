package edu.project.englishstoriesbot.exeptions;

public class FailedToLoadAuthorsException extends Exception{

    public FailedToLoadAuthorsException() {
    }

    public FailedToLoadAuthorsException(String message) {
        super(message);
    }

    public FailedToLoadAuthorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToLoadAuthorsException(Throwable cause) {
        super(cause);
    }

    public FailedToLoadAuthorsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
