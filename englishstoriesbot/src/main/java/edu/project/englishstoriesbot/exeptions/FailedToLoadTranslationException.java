package edu.project.englishstoriesbot.exeptions;

public class FailedToLoadTranslationException extends Exception{

    public FailedToLoadTranslationException() {
    }

    public FailedToLoadTranslationException(String message) {
        super(message);
    }

    public FailedToLoadTranslationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToLoadTranslationException(Throwable cause) {
        super(cause);
    }

    public FailedToLoadTranslationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
