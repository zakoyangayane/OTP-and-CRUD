package com.task.auth.otp.repository;


import com.task.auth.otp.entity.OTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntity, Long> {

    Optional<OTPEntity> findByOTPAndExpiredFalse(String OTP);

    @Modifying
    @Transactional
    @Query("UPDATE OTPEntity e SET e.expired = true WHERE e.identifier = ?1 ")
    void updateAllStatusFromIdentifier(String identifier);

    boolean existsByOTP(String OTP);

    @Modifying
    @Transactional
    @Query("UPDATE OTPEntity e SET e.expired = true WHERE e.OTP =?1")
    void setOTPExpired(String OTP);

    @Modifying
    @Transactional
    @Query("UPDATE OTPEntity e SET e.expired = true, e.authorized = true WHERE e.OTP =?1")
    void setOTPExpiredAndAuthorized(String otp);
}
