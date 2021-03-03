package com.task.user.service;


import com.task.user.api.request.UserRequest;
import com.task.user.api.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getUserById(Long id);

    boolean deleteUserById(Long id);

    UserResponse creatUserByToken(UserRequest userRequest);

    UserResponse updateUserById(UserRequest userRequest, Long id);

    List<UserResponse> getAllUsers( int pageNumber, int pageSize, String sortBy, String sortOrder);

}
