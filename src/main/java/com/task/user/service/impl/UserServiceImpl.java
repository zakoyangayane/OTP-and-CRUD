package com.task.user.service.impl;


import com.task.base.messages.ExceptionConstant;
import com.task.exception.NotFoundException;
import com.task.exception.NotReadException;
import com.task.security.jwt.JwtValidator;
import com.task.user.api.request.UserRequest;
import com.task.user.api.response.UserResponse;
import com.task.user.entity.UserEntity;
import com.task.user.mapper.UserMapper;
import com.task.user.repository.UserRepository;
import com.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtValidator jwtValidator;

    @Override
    @SneakyThrows
    public UserResponse getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionConstant.USER_NOT_FOUND_EXCEPTION, HttpStatus.BAD_REQUEST));

        return userMapper.fromUserEntityToUserResponse(userEntity);
    }

    @Override
    @SneakyThrows
    public boolean deleteUserById(Long id) {
        if (!userRepository.existsById(id))
            throw new NotFoundException(ExceptionConstant.USER_NOT_FOUND_EXCEPTION, HttpStatus.BAD_REQUEST);

        userRepository.deleteById(id);
        return true;
    }

    @Override
    @SneakyThrows
    public UserResponse creatUserByToken(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new NotReadException(ExceptionConstant.EMAIL_ALREADY_USED_EXCEPTION, HttpStatus.BAD_REQUEST);

        UserEntity entity = userMapper.fromUserRequestToUserEntity(userRequest);

        UserEntity createdUser = userRepository.save(entity);

        return userMapper.fromUserEntityToUserResponse(createdUser);
    }

    @Override
    @SneakyThrows
    public UserResponse updateUserById(UserRequest userRequest, Long id) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ExceptionConstant.USER_NOT_FOUND_EXCEPTION, HttpStatus.BAD_REQUEST));

        if (userRepository.existsByEmailAndIdNotEquals(userRequest.getEmail(), id))
            throw new NotReadException(ExceptionConstant.EMAIL_ALREADY_USED_EXCEPTION, HttpStatus.BAD_REQUEST);

        entity = userMapper.fromUserRequestToUserEntityWithSameId(userRequest, entity);

        UserEntity createdUser = userRepository.save(entity);

        return userMapper.fromUserEntityToUserResponse(createdUser);
    }

    @Override
    public List<UserResponse> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortOrder) {


        List<UserEntity> userEntities = userRepository.findAll(PageRequest.of(pageNumber, pageSize,
                Sort.by(Sort.Direction.fromString(sortOrder), sortBy))).getContent();

        return userMapper.fromUserEntityListToUserResponseList(userEntities);
    }

}
