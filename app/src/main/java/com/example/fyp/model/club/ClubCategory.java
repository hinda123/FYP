package com.example.fyp.model.club;
import java.util.UUID;

public class ClubCategory {
    private final UUID id;
    private final String clubTitle;
    private final String imageCover;


    public ClubCategory(UUID id, String clubTitle, String imageCover) {
        this.id = id;
        this.clubTitle = clubTitle;
        this.imageCover = imageCover;
    }

    public UUID getId() {
        return id;
    }

    public String getClubTitle() {
        return clubTitle;
    }

    public String getImageCover() {
        return imageCover;
    }

}
