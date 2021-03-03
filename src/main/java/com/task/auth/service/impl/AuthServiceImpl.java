package com.task.auth.service.impl;

import com.task.auth.otp.entity.OTPEntity;
import com.task.auth.otp.repository.OTPRepository;
import com.task.auth.service.AuthService;
import com.task.base.messages.ExceptionConstant;
import com.task.base.utils.DateUtils;
import com.task.base.utils.VerificationUtils;
import com.task.email.service.EmailService;
import com.task.exception.NotFoundException;
import com.task.exception.NotReadException;
import com.task.security.jwt.JwtUtils;
import com.task.user.entity.UserEntity;
import com.task.user.repository.UserRepository;
import com.task.validator.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmailValidator emailValidator;

    private final EmailService emailService;

    private final JwtUtils jwtUtils;

    private final OTPRepository OTPRepository;

    private final UserRepository userRepository;


    /*
     * Verification flow
     */

    @Override
    @SneakyThrows
    public boolean verifyEmail(final String email) {

        boolean isValidEmail = emailValidator.isValidEmailAddress(email);

        if (!isValidEmail) {
            return false;
        }

        if (!userRepository.existsByEmail(email)) {
            throw new NotFoundException(ExceptionConstant.USER_NOT_FOUND_EXCEPTION, HttpStatus.BAD_REQUEST);
        }

        // get verification table like email
        OTPRepository.updateAllStatusFromIdentifier(email);

        // generate email code
        String OTP = VerificationUtils.getRandomNumbers();

        while (OTPRepository.existsByOTP(OTP)) {
            OTP = VerificationUtils.getRandomNumbers();
        }

        // send code to corresponding email
        emailService.sendEmail(email, "Verification.", OTP);

        // save code to database
        OTPRepository.save(new OTPEntity(OTP, email, false, false));
        return true;
    }


    @Override
    @SneakyThrows
    public String verifyOTP(final String OTP) {

        OTPEntity OTPEntity =
                OTPRepository.findByOTPAndExpiredFalse(OTP)
                        .orElseThrow(() -> new NotReadException(ExceptionConstant.OTP_NOT_FOUND_EXCEPTION, HttpStatus.BAD_REQUEST));

        // check email one time password is expired
        if (!DateUtils.isGreat(new Date(), OTPEntity.getCreatedDate())) {
            OTPRepository.setOTPExpired(OTP);
            return null;
        }

        OTPRepository.setOTPExpiredAndAuthorized(OTP);

        UserEntity userEntity = userRepository.findByEmail(OTPEntity.getIdentifier()).orElseThrow(() -> new NotFoundException(
                ExceptionConstant.USER_NOT_FOUND_EXCEPTION,
                HttpStatus.BAD_REQUEST));

        return jwtUtils.generateJwtToken(userEntity);

    }
}
