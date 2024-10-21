package com.mercaline.users;

import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    public void testFindByUsername() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("UsuarioTest")
                .password("password123")
                .name("name1")
                .lastname("name2")
                .email("test@domain.com")
                .tel("+34696256687")
                .build();

        userEntityRepository.save(user);

        // Encontrar el usuario por username
        Optional<UserEntity> foundUser = userEntityRepository.findByUsername("UsuarioTest");
        assertTrue(foundUser.isPresent());
        assertEquals("UsuarioTest", foundUser.get().getUsername());
    }
}
