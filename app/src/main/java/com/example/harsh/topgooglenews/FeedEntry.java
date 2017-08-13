package com.example.harsh.topgooglenews;


public class FeedEntry {

    private String title;
    private String url;
    private String publishedAt;
    private String description;
    private String urlToImage;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return urlToImage;
    }

    public void setImageURL(String imageURL) {
        this.urlToImage = imageURL;
    }

//    @Override
//    public String toString() {
//        return "name=" + name + '\n' +
//                ", artist=" + artist + '\n' +
//                ", releaseDate=" + releaseDate + '\n' +
//                ", imageURL=" + imageURL + '\n';
//    }
}
