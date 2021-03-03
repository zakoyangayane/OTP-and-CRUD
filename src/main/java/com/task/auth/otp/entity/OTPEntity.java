package com.task.auth.otp.entity;


import com.task.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OTP")
@EqualsAndHashCode(callSuper = true)
public class OTPEntity extends BaseEntity {

    private String OTP;

    private String identifier;

    private boolean authorized;

    private boolean expired;

}
