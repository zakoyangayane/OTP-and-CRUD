package com.task.auth.controller;


import com.task.auth.api.AuthApi;
import com.task.auth.api.email.request.EmailVerificationRequest;
import com.task.auth.api.email.response.EmailVerificationResponse;
import com.task.auth.service.AuthService;
import com.task.base.response.BaseResponse;
import com.task.validator.RequestFieldsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final RequestFieldsValidator requestFieldsValidator;

    private final AuthService authService;

    @Override
    public ResponseEntity<BaseResponse<EmailVerificationResponse>>
    emailVerification(final @Valid EmailVerificationRequest request, Errors errors) {

        requestFieldsValidator.validate(request);

        requestFieldsValidator.validate(errors);

        String verificationResultMessage;

        boolean verifyEmail = authService.verifyEmail(request.getEmail());

        verificationResultMessage = (verifyEmail)
                ? "Code sent to email."
                : "Not passed verification";

        EmailVerificationResponse response = EmailVerificationResponse.builder()
                .validEmail(verifyEmail)
                .build();
        BaseResponse<EmailVerificationResponse> baseResponse =
                new BaseResponse<>(
                        verifyEmail, verificationResultMessage, response);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<BaseResponse<String>>
    verifyOneTimePassword(final String OTP) {

        requestFieldsValidator.validate(OTP);

        String verificationResultMessage;

        String token = authService.verifyOTP(OTP);
        verificationResultMessage = token != null
                ? "One Time Password verification successfully."
                : "Not valid One Time Password, please try again.";

        boolean success = token != null;

        BaseResponse<String> baseResponse =
                new BaseResponse<>(
                        success, verificationResultMessage, token);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
