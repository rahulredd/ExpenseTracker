package com.challenge.dao;

import com.challenge.pojo.User;

public interface IUserDAO {
	
	public boolean createUser(String username, String password, String email);
	public User findUser(String username);
}
