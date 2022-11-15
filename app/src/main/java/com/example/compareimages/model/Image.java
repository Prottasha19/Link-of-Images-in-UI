package com.example.compareimages.model;

public class Image {
    private String imageID;
    private String imgLink;

    public Image() {
    }

    public Image(String imageID, String imgLink) {
        this.imageID = imageID;
        this.imgLink = imgLink;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
