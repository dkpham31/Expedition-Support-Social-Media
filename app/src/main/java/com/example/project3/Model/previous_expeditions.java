package com.example.project3.Model;

public class previous_expeditions {
    private String Title;
    private   String Long;
    private String Length;
    private String ImageCover;

    public previous_expeditions(){

    }

    public previous_expeditions(String Title, String Long, String Length, String ImageCover) {
        this.Title = Title;
        this.Long = Long;
        this.Length = Length;
        this.ImageCover = ImageCover;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String Long) {
        this.Long = Long;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String Length) {
        this.Length = Length;
    }

    public String getImageCover() {
        return ImageCover;
    }

    public void setImageCover(String ImageCover) {
        this.ImageCover = ImageCover;
    }
}
