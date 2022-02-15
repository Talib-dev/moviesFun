package com.we.ilmuro;

public class model {
    private int id;
    private String image;
    private String title;
    private String story;
    private String author;
    private String authoravatar;
    private  String date;
    private  String name;
    private int readingtime;
    private String hashtag;
    private String hashtagcolor;

    public model(int id, String image, String title, String story, String author, String authoravatar, String date, String name, int readingtime, String hashtag, String hashtagcolor) {

        this.id = id;
        this.image = image;
        this.title = title;
        this.story = story;
        this.author = author;
        this.authoravatar = authoravatar;
        this.date = date;
        this.name = name;
        this.readingtime = readingtime;
        this.hashtag = hashtag;
        this.hashtagcolor = hashtagcolor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthoravatar() {
        return authoravatar;
    }

    public void setAuthoravatar(String authoravatar) {
        this.authoravatar = authoravatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadingtime() {
        return readingtime;
    }

    public void setReadingtime(int readingtime) {
        this.readingtime = readingtime;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getHashtagcolor() {
        return hashtagcolor;
    }

    public void setHashtagcolor(String hashtagcolor) {
        this.hashtagcolor = hashtagcolor;
    }
}
