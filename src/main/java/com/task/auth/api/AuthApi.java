package com.task.auth.api;


import com.task.auth.api.email.request.EmailVerificationRequest;
import com.task.auth.api.email.response.EmailVerificationResponse;
import com.task.base.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@RequestMapping("/auth")
public interface AuthApi {

    @PostMapping("/verification/email")
    ResponseEntity<BaseResponse<EmailVerificationResponse>> emailVerification(
            @RequestBody @Nullable @Valid EmailVerificationRequest request, Errors errors
    );


    @PostMapping("/verification/email/one-time-password")
    ResponseEntity<BaseResponse<String>> verifyOneTimePassword(
            @RequestParam("OTP") @Nullable String OTP
    );

}
