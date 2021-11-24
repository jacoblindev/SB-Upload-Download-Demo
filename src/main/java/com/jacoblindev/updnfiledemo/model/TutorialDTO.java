package com.jacoblindev.updnfiledemo.model;

public class TutorialDTO {
    private String title;
    private String description;
    private int published;

    public TutorialDTO() {
    }

    public TutorialDTO(String title, String description, int published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

}
