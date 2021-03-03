package com.task.user.controller;


import com.task.base.messages.ResponseMessageConstant;
import com.task.base.response.BaseResponse;
import com.task.user.api.UserApi;
import com.task.user.api.request.UserRequest;
import com.task.user.api.response.UserResponse;
import com.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(String token, Long id) {
        UserResponse userResponse = userService.getUserById(id);
        BaseResponse<UserResponse> baseResponse = new BaseResponse<>(true, ResponseMessageConstant.GET, userResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse<Boolean>> deleteUserById(String token, Long id) {
        Boolean isDeleted = userService.deleteUserById(id);
        BaseResponse<Boolean> baseResponse = new BaseResponse<>(isDeleted, ResponseMessageConstant.DELETED, isDeleted);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponse>> createUserByToken(String token, UserRequest userRequest) {
        UserResponse userResponse = userService.creatUserByToken(userRequest);
        BaseResponse<UserResponse> baseResponse = new BaseResponse<>(true, ResponseMessageConstant.SAVED, userResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponse>> updateUserById(Long id, String token, UserRequest userRequest) {
        UserResponse userResponse = userService.updateUserById(userRequest, id);
        BaseResponse<UserResponse> baseResponse = new BaseResponse<>(true, ResponseMessageConstant.UPDATED, userResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse<List<UserResponse>>> getAllUsers(String token, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        List<UserResponse> userResponse = userService.getAllUsers(pageNumber, pageSize, sortBy, sortOrder);
        BaseResponse<List<UserResponse>> baseResponse = new BaseResponse<>(true, ResponseMessageConstant.GET, userResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
