package com.challenge.dao;

import org.hibernate.Session;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.Assert;

public class UserDAOTest {

	@Rule
	public final SessionFactoryRule sessionFactoryRule = new SessionFactoryRule();
	UserDAO user = new UserDAO();
	private static final String USERNAME = "test";
	private static final String PASSWORD = "abcd";
	private static final String EMAIL = "test@gmail.com";
	
	private void addUser(String username, String password, String email, Session session) {
		session.beginTransaction();
		user.createUser(username, password, email,session);
	}
	
	@Test
	public void testLogin() {
		Session session = sessionFactoryRule.getSession();
		addUser(USERNAME, PASSWORD, EMAIL, session);
		Assert.assertTrue(user.verifyLogin(USERNAME, PASSWORD, session));
	}
	
	@Test
	public void addUser() {
		Session session = sessionFactoryRule.getSession();
		addUser(USERNAME, PASSWORD, EMAIL, session);
		Assert.assertEquals(USERNAME, user.findUser(USERNAME, session).getUsername());
	}
	
	@Test
	public void testFindUser() {
		Session session = sessionFactoryRule.getSession();

		String username = "test1";
		String password = "abcd";
		String email = "test1@gmail.com";
		
		addUser(username, password, email, session);
		addUser(USERNAME, PASSWORD, EMAIL, session);
		Assert.assertEquals(username, user.findUser(username, session).getUsername());
	}
}
