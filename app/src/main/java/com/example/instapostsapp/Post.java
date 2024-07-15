package com.example.instapostsapp;
public class Post {
    private int id;
    private byte[] image;
    private int likes;
    private int comments;
    private boolean isLiked;

    public Post(int id, byte[] image, int likes, int comments, boolean isLiked) {
        this.id = id;
        this.image = image;
        this.likes = likes;
        this.comments = comments;
        this.isLiked = isLiked;
    }

    public int getId() { return id; }
    public byte[] getImage() { return image; }
    public int getLikes() { return likes; }
    public int getComments() { return comments; }
    public boolean isLiked() { return isLiked; }

    public void setLikes(int likes) { this.likes = likes; }
    public void setComments(int comments) { this.comments = comments; }
    public void setLiked(boolean liked) { isLiked = liked; }
}

