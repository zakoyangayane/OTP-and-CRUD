package com.task.auth.service;

public interface AuthService {

    /*
     * Verification flow
     */

    boolean verifyEmail(String email);

    String verifyOTP(String OTP);

}
