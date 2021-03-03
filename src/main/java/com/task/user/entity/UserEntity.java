package com.task.user.entity;


import com.task.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity {

    private String email;
    private String firstName;
    private String lastName;
    private Long birthDate;
    private int maritalStatus;

}
