package com.example.fyp.model.club;
import java.util.UUID;

public class ClubCategory {
    private  UUID id;
    private  String clubType;
    private  String imageIcon;

    public ClubCategory() {
    }

    public ClubCategory(UUID id, String clubType, String imageIcon) {
        this.id = id;
        this.clubType = clubType;
        this.imageIcon = imageIcon;
    }

    public UUID getId() {
        return id;
    }

    public String getClubType() {
        return clubType;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    @Override
    public String toString() {
        return "ClubCategory{" +
                "id=" + id +
                ", clubType='" + clubType + '\'' +
                ", imageIcon='" + imageIcon + '\'' +
                '}';
    }
}
