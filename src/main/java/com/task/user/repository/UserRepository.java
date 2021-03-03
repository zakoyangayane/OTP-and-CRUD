package com.task.user.repository;

import com.task.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("select case when count(e)> 0 then true else false end from UserEntity e where lower(e.email) like lower(:email)")
    Boolean existsByEmail(@Param("email") String email);

    @Query("select case when count(e)> 0 then true else false end from UserEntity e where lower(e.email) like lower(:email) AND e.id <> (:id)")
    Boolean existsByEmailAndIdNotEquals(@Param("email") String email, @Param("id") Long id);

}
