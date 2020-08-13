package edu.project.englishstoriesbot.cache;

public class UserData {
    private String poemTitle;
    private String authorName;

    public UserData() {
    }

    public UserData(String poemTitle, String authorName) {
        this.poemTitle = poemTitle;
        this.authorName = authorName;
    }

    public String getPoemTitle() {
        return poemTitle;
    }

    public void setPoemTitle(String poemTitle) {
        this.poemTitle = poemTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
