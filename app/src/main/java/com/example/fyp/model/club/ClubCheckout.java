package com.example.fyp.model.club;


public class ClubCheckout {
    private final String clubCategory;
    private final Club club;

    public ClubCheckout(String clubCategory, Club club) {
        this.clubCategory = clubCategory;
        this.club = club;
    }

    public String getClubCategory() {
        return clubCategory;
    }

    public Club getClub() {
        return club;
    }
}
