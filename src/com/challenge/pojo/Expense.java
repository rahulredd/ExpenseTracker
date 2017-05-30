package com.challenge.pojo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Expense {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	private User user;
	private double amount;
	private String description;
	
	@Type(type="timestamp")
	private Date created;
	
	public Expense() {
		this.created = new Date();
	}
	
	public Expense(User user, double amount, String description) {
		this.user = user;
		this.amount = amount;
		this.description = description;
		this.created = new Date();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
}
