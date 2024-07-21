package com.jwtexample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.jwtexample.dto.User;

@Service
public class UserService {

	private List<User> users = new ArrayList<>();

	public UserService() {

		users.add(new User(UUID.randomUUID().toString(), "Amrit", "Amrit@gmail.com"));
		users.add(new User(UUID.randomUUID().toString(), "Rajan", "Rajan@gmail.com"));
		users.add(new User(UUID.randomUUID().toString(), "Rajesh", "Rajesh@gmail.com"));
		users.add(new User(UUID.randomUUID().toString(), "Sonali", "Sonali@gmail.com"));

	}
	
	public List<User> getUsers(){
		return this.users;
	}

}
