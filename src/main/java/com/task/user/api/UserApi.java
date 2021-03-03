package com.task.user.api;

import com.task.base.response.BaseResponse;
import com.task.user.api.request.UserRequest;
import com.task.user.api.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface UserApi {

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<UserResponse>> getUserById(@RequestHeader(name = "Authorization") String token, @PathVariable Long id);

    @DeleteMapping
    ResponseEntity<BaseResponse<Boolean>> deleteUserById(@RequestHeader(name = "Authorization") String token, Long id);

    @PostMapping
    ResponseEntity<BaseResponse<UserResponse>> createUserByToken(@RequestHeader(name = "Authorization") String token,
                                                                 @RequestBody UserRequest userRequest);

    @PutMapping("/{id}")
    ResponseEntity<BaseResponse<UserResponse>> updateUserById(@PathVariable Long id,
                                                              @RequestHeader(name = "Authorization") String token,
                                                              @RequestBody UserRequest userRequest);


    @GetMapping
    ResponseEntity<BaseResponse<List<UserResponse>>> getAllUsers(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "createdDate") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc") String sortOrder);

}
