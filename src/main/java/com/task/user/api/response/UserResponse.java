package com.task.user.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private Long birthDate;
    private int maritalStatus;

    private Date createdDate;
    private Date lastModifiedDate;
}
