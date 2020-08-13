package edu.project.englishstoriesbot.model;

import java.util.List;

public class Author implements Sendable{

    private String name;
    private List<String> allPoemTitles;

    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAllPoemTitles() {
        return allPoemTitles;
    }

    public void setAllPoemTitles(List<String> allPoemTitles) {
        this.allPoemTitles = allPoemTitles;
    }
}
