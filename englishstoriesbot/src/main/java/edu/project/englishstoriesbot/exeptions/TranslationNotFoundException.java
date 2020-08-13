package edu.project.englishstoriesbot.exeptions;

public class TranslationNotFoundException extends Exception{

    public TranslationNotFoundException() {
    }

    public TranslationNotFoundException(String message) {
        super(message);
    }

    public TranslationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranslationNotFoundException(Throwable cause) {
        super(cause);
    }

    public TranslationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
