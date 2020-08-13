package edu.project.englishstoriesbot.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Expression {

    private Set<String> originalExpression = new HashSet<>();
    private List<String> partOfSpeech  = new ArrayList<>();
    private List<String> synonyms  = new ArrayList<>();
    private List<String> translations  = new ArrayList<>();
    private List<String> examples = new ArrayList<>();

    public Expression() {
    }

    public Set<String> getOriginalExpression() {
        return originalExpression;
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

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "originalExpression=" + originalExpression +
                ", partOfSpeech=" + partOfSpeech +
                ", synonyms=" + synonyms +
                ", translations=" + translations +
                ", examples=" + examples +
                '}';
    }
}
