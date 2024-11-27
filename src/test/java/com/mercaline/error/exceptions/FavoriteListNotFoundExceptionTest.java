package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavoriteListNotFoundExceptionTest {
	
	@InjectMocks
	private FavoriteListNotFoundException favoriteListNotFoundException;

	@Test
	void test() {
		assertNotNull(new FavoriteListNotFoundException());
	}

}
