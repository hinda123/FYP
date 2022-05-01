package com.example.fyp.service;

import com.example.fyp.model.club.Club;
import com.example.fyp.model.club.ClubCategory;

import java.util.List;
import java.util.UUID;

public interface ClubService {
    void joinClub(UUID studentId);
    void payPrice(UUID studentId);
    List<Club> listAllClubsByClubCategory(String clubCategory);
    List<ClubCategory> listClubCategory();
    Club findClubById(UUID id);
}
