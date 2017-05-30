package com.challenge.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.db.DbUtils;
import com.challenge.pojo.User;

public class UserDAO implements IUserDAO {
	public static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
	private static Session session;

	private static void beginSession() {
		session = DbUtils.getSessionFactory().openSession();
		session.beginTransaction();
	}

	@Override
	public boolean createUser(String username, String password, String email) {
		beginSession();
		boolean isUserCreated = createUser(username, password, email, session);
		session.close();
		return isUserCreated;
	}

	public boolean createUser(String username, String password, String email, Session session) {
		int admin = 0;
		String hashPw = encrypt(password);
		User user = new User();
		user.setAdmin(admin);
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(hashPw);

		try {
			session.save(user);
			session.getTransaction().commit();
		} catch (SQLGrammarException e) {
			session.getTransaction().rollback();
			LOG.error("Cannot save user", e);
			return false;
		}
		return true;
	}

	@Override
	public User findUser(String username) {
		beginSession();
		User user = findUser(username, session);
		session.close();
		return user;
		
	}
	
	public User findUser(String username, Session session) {
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.like("username", username));
		List<User> user = (List<User>) criteria.list();
		if (user != null) {
			return user.get(0);
		}
		return new User();
	}

	public boolean verifyLogin(String username, String password) {
		beginSession();
		boolean success = verifyLogin(username, password, session);
		session.close();
		return success;
	}
	
	public boolean verifyLogin(String username, String password, Session session) {
		User user = findUser(username, session);
		if (user != null) {
			String savedPassword = user.getPassword();
			if (BCrypt.checkpw(password, savedPassword)) {
				return true;
			}
		}
		return false;
	}

	private static String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

}
