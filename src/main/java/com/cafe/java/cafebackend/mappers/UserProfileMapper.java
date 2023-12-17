package com.cafe.java.cafebackend.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.wrappers.UserWrapper;

@Service
public class UserProfileMapper {
    public  List<UserWrapper> userProfileMapper(List<UserProfile> userProfile){
        List<UserWrapper> result = new ArrayList<>();
        for (UserProfile user : userProfile) {
            result.add(new UserWrapper(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserPhoneNo(), user.getStatus()));
            }
            return result;
    }
}
