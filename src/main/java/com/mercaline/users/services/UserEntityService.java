package com.mercaline.users.services;

import java.util.Optional;

import com.mercaline.service.base.BaseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, Long, UserEntityRepository> {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserEntity> findUserByUsername(String name) {
        return this.userEntityRepository.findByUsername(name);
    }

    public UserEntity newUser(UserEntity newUser) {
        //PENDIENTE - try-catch
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return this.userEntityRepository.save(newUser);
	}

    public Optional<UserEntity> getUser(Long id) {
        return this.userEntityRepository.findById(id);
    }


}
