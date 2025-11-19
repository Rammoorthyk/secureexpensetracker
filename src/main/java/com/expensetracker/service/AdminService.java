package com.expensetracker.service;
import com.expensetracker.model.User;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

	@Autowired
	private ExpenseRepository expenserepository;
	
	@Autowired
	private UserRepository userrepository;
	
	public List<User> getAllUsers(){
		return userrepository.findAll();
		
	}
	 public List<Expense> getUserExpenses(Long userId) { 
	        return expenserepository.findByUserId(userId);  
	    }
	
	
	
	
}
