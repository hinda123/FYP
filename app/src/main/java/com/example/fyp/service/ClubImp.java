package com.example.fyp.service;

import com.example.fyp.model.club.Club;
import com.example.fyp.model.club.ClubCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClubImp implements ClubService{


    @Override
    public void joinClub(UUID studentId) {

    }

    @Override
    public void payPrice(UUID studentId) {

    }

    @Override
    public List<Club> listAllClubsByClubCategory(String clubCategory) {
        List<Club> clubs = new ArrayList<>();
        clubs.add(new Club(UUID.randomUUID(),"Football club",23.00,"des....", "April 2,2022","https://m.media-amazon.com/images/I/51KM6J9JUBL._AC_SL1024_.jpg"));
        clubs.add(new Club(UUID.randomUUID(),"Swimming",21.21,"des....", "April 2,2022","https://m.media-amazon.com/images/I/51KM6J9JUBL._AC_SL1024_.jpg"));
        clubs.add(new Club(UUID.randomUUID(),"Basketball",52.2,"des....", "April 1,2022","https://m.media-amazon.com/images/I/51KM6J9JUBL._AC_SL1024_.jpg"));
        clubs.add(new Club(UUID.randomUUID(),"Tennis",31.12,"des....", "April 2,2022","https://m.media-amazon.com/images/I/51KM6J9JUBL._AC_SL1024_.jpg"));
        return clubs;
    }

    @Override
    public List<ClubCategory> listClubCategory() {
        List<ClubCategory>  clubCategories = new ArrayList<>();
        clubCategories.add(new ClubCategory(UUID.randomUUID(),"Sports","https://media.npr.org/assets/img/2020/06/10/gettyimages-200199027-001_wide-3ff0f063a2bf1ab01550d3508c816bc43009d215.jpg?s=1400"));
        clubCategories.add(new ClubCategory(UUID.randomUUID(),"Academic","https://media.npr.org/assets/img/2020/06/10/gettyimages-200199027-001_wide-3ff0f063a2bf1ab01550d3508c816bc43009d215.jpg?s=1400"));
        clubCategories.add(new ClubCategory(UUID.randomUUID(),"Arts","https://media.npr.org/assets/img/2020/06/10/gettyimages-200199027-001_wide-3ff0f063a2bf1ab01550d3508c816bc43009d215.jpg?s=1400"));
        clubCategories.add(new ClubCategory(UUID.randomUUID(),"Cultural","https://media.npr.org/assets/img/2020/06/10/gettyimages-200199027-001_wide-3ff0f063a2bf1ab01550d3508c816bc43009d215.jpg?s=1400"));
        clubCategories.add(new ClubCategory(UUID.randomUUID(),"Religious","https://media.npr.org/assets/img/2020/06/10/gettyimages-200199027-001_wide-3ff0f063a2bf1ab01550d3508c816bc43009d215.jpg?s=1400"));
        clubCategories.add(new ClubCategory(UUID.randomUUID(),"Special","https://media.npr.org/assets/img/2020/06/10/gettyimages-200199027-001_wide-3ff0f063a2bf1ab01550d3508c816bc43009d215.jpg?s=1400"));
        return clubCategories;
    }

    @Override
    public Club findClubById(UUID id) {
        return null;
    }
}
