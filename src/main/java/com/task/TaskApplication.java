package com.task;

import com.task.user.entity.UserEntity;
import com.task.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@EnableJpaAuditing
@ComponentScan({"com"})
@SpringBootApplication
@RequiredArgsConstructor
@EntityScan(basePackages = {("com.task")})
@EnableJpaRepositories(basePackages = {("com.task")})
public class TaskApplication implements CommandLineRunner {

    private final UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) {

        List<UserEntity> userEntityLis = new ArrayList<>();

        userEntityLis.add(new UserEntity("onemail@gmail.com", "Name", "Surname", 1795464L, 1));
        userEntityLis.add(new UserEntity("twomail@gmail.com", "Name", "Surname", 1795464L, 1));
        userEntityLis.add(new UserEntity("threemail@gmail.com", "Name", "Surname", 1795464L, 1));
        userEntityLis.add(new UserEntity("fourmail@gmail.com", "Name", "Surname", 1795464L, 1));

        if (userRepository.count() == 0)
            userRepository.saveAll(userEntityLis);
    }

}
