package com.jwtexample.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jwtexample.dto.User;
import com.jwtexample.service.UserService;

@RestController
@RequestMapping("/home")
public class HomeController {

	@Autowired
	public UserService service;

	public void setService(UserService service) {
		this.service = service;
	}

	@GetMapping("/getusers")
	public List<User> getUSers() {
		return service.getUsers();

	}
	
	@GetMapping("/getloggedinuser")
	public String getLoggedInUser(Principal principal) {
		return principal.getName();

	}
	
	

}
