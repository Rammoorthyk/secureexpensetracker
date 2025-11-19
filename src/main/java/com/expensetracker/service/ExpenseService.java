package com.expensetracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;


@Service
public class ExpenseService {

	@Autowired
	private UserRepository userrepository;
	@Autowired
	private ExpenseRepository expenserepository;
	
	
	public Expense create(Expense expense,String username) {
		User user=userrepository.findByUsername(username)
				.orElseThrow(()->new RuntimeException("user not found"+username));
		expense.setUser(user);
		return expenserepository.save(expense);
	}
	public List<Expense> getAll(String username){
		User user=userrepository.findByUsername(username)
				.orElseThrow(()->new RuntimeException("user not found"+username));
			return expenserepository.findByUserId(user.getId());	
	}
	
	

    public Expense getById(Long id, String username) {
        Expense expense = expenserepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        
        if (!expense.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access");
        }

        return expense;
    }

    public void delete(Long id, String username) {
        Expense expense = getById(id, username);
        expenserepository.delete(expense);
    }
	
    public Expense update(Long id,Expense newExpense,String username) {
    	Expense existingExpense=getById(id,username);
    	existingExpense.setDescription(newExpense.getDescription());
    	existingExpense.setAmount(newExpense.getAmount());
    	existingExpense.setDate(newExpense.getDate());
    	return expenserepository.save(existingExpense);
    }
    
}
