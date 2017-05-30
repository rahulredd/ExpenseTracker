package com.challenge.pojo;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String username;
	private String password;
	private String email;
	private int admin; // 1 - admin, 0 - non-admin
	
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Expense> expenses = new ArrayList<>();
	
  
  public User() {
  	
  }
  
  public User(String username, String password, String email, int admin) {
  		this.username = username;
  		this.password = password;
  		this.email = email;
  		this.admin = admin;
  }
  
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Expense> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		if (admin != 1 && admin != 0) {
			throw new IllegalArgumentException("set 0 for non-admin. 1 for admin");
		}
		this.admin = admin;
	}
}
