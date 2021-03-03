package com.task.user.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {


    private String email;
    private String firstName;
    private String lastName;
    private Long birthDate;
    private int maritalStatus;

}
