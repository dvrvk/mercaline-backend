package com.mercaline.users.model;

import com.mercaline.users.Model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class UserEntityTest.
 */
@SpringBootTest
public class UserEntityTest {

    /**
     * Test not null user.
     */
    @Test
    public void testNotNullUser() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("UsuarioTest")
                .password("password123")
                .email("test@domain.com")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("UsuarioTest", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@domain.com", user.getEmail());
    }

    /**
     * Test all user.
     */
    @Test
    public void testAllUser() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("UsuarioTest")
                .password("password123")
                .name("name1")
                .lastname("name2")
                .email("test@domain.com")
                .tel("+34696256687")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("UsuarioTest", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@domain.com", user.getEmail());
        assertEquals("name1", user.getName());
        assertEquals("name2", user.getLastname());
        assertEquals("+34696256687", user.getTel());
    }

}
