package com.example.finalprojectapp;

/**
 * Model class to store single article details
 */
public class NewsArticle {

    private long id = -1;
    private String webTitle;
    private String webUrl;
    private String webPublicationDate;
    private String sectionName;

    public NewsArticle(String webTitle, String webUrl, String webPublicationDate, String sectionName) {
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.webPublicationDate = webPublicationDate;
        this.sectionName = sectionName;
    }

    public NewsArticle(long id, String webTitle, String webUrl, String webPublicationDate, String sectionName) {
        this.id = id;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.webPublicationDate = webPublicationDate;
        this.sectionName = sectionName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
