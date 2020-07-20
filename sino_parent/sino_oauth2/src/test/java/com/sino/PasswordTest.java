package com.sino;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordTest {

	@Test
	void test() {
		System.out.println(new BCryptPasswordEncoder().encode("111"));
	}

}
