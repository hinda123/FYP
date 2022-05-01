package com.example.fyp.model.club;

import java.time.LocalDateTime;
import java.util.UUID;

public class Club {
    private final UUID id;
    private final String title;
    private final Double price;
    private final String description;
    private final String date;
    private final String imageIcon;

    public Club(UUID id, String title, Double price, String description, String date, String imageIcon) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.date = date;
        this.imageIcon = imageIcon;
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

    public String getImageIcon() {
        return imageIcon;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", imageIcon='" + imageIcon + '\'' +
                '}';
    }
}
