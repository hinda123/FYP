package com.example.fyp.util.club;

import com.example.fyp.service.ClubImp;
import com.example.fyp.service.ClubService;

public class ClubServiceUtil {
    private static ClubService clubService;

    public static ClubService createClubService() {
        if (clubService == null)
            clubService = new ClubImp();
        return clubService;
    }

}
