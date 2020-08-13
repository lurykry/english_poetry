package edu.project.englishstoriesbot.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Expression implements Sendable {

    private Set<String> originalExpression = new HashSet<>();
    private List<String> partOfSpeech  = new ArrayList<>();
    private List<String> translations  = new ArrayList<>();


    public Expression() {
    }

    public Set<String> getOriginalExpression() {
        return originalExpression;
    }


    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public void setOriginalExpression(Set<String> originalExpression) {
        this.originalExpression = originalExpression;
    }

    public List<String> getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(List<String> partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "originalExpression=" + originalExpression +
                ", partOfSpeech=" + partOfSpeech +
                ", translations=" + translations +
                '}';
    }
}
