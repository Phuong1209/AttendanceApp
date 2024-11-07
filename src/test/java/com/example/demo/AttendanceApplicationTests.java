package com.example.demo;

import com.example.demo.model.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class AttendanceApplicationTests {
	@Autowired
	private PasswordEncoder passwordEncoder;
	private User user;
	@Test
	void contextLoads() {
		final List<String> INPUT_LIST = Lists.newArrayList("250901", "070201", "140301","1209","2110","2111");
		for(String password : INPUT_LIST) {
			String pass = passwordEncoder.encode(password);
			System.out.println( password + "PasswordEncoded" + pass);
		}
	}
}
