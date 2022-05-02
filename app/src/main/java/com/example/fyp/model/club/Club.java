package com.example.fyp.model.club;

import java.util.UUID;

public class Club {
    private  UUID id;
    private  String title;
    private  Double price;
    private  String description;
    private  String date;
    private  String imageCover;

    public Club() {
    }

    public Club(UUID id, String title, Double price, String description, String date, String imageCover) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.date = date;
        this.imageCover = imageCover;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImageCover() {
        return imageCover;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", imageIcon='" + imageCover + '\'' +
                '}';
    }
}
