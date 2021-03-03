package com.task.user.mapper;


import com.task.user.api.request.UserRequest;
import com.task.user.api.response.UserResponse;
import com.task.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;

    public UserResponse fromUserEntityToUserResponse(UserEntity userEntity) {
        return mapper.map(userEntity, new TypeToken<UserResponse>() {
        }.getType());
    }

    public UserEntity fromUserRequestToUserEntity(UserRequest userRequest) {
        return mapper.map(userRequest, new TypeToken<UserEntity>() {
        }.getType());
    }

    public UserEntity fromUserRequestToUserEntityWithSameId(UserRequest userRequest, UserEntity userEntity) {
        UserEntity updatedUser = UserEntity.builder()
                .birthDate(userRequest.getBirthDate())
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .maritalStatus(userRequest.getMaritalStatus())
                .lastName(userRequest.getLastName())
                .build();

        updatedUser.setId(userEntity.getId());
        updatedUser.setCreatedDate(userEntity.getCreatedDate());
        return updatedUser;
    }

    public List<UserResponse> fromUserEntityListToUserResponseList(List<UserEntity> userEntities) {
        return mapper.map(userEntities, new TypeToken<List<UserResponse>>() {
        }.getType());
    }
}
