package com.authify.service;

import com.authify.io.ProfileRequest;
import com.authify.io.ProfileResponse;

public interface ProfileService {

    public ProfileResponse createProfile(ProfileRequest request);

    ProfileResponse getProfile(String email);

    void sendResendOtp(String email);

    void resetPassword(String email,String otp,String newPassword);

    void sendOtp(String email);

    void verifyOtp(String email,String Otp);

    long getrole(String email);
}

