package com.expensetracker.controller;


import com.expensetracker.model.Expense;
import com.expensetracker.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.expensetracker.service.ExpenseService;
import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

	private final ExpenseService expenseService;
	//private final ExpenseService expenseService;
	
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService=expenseService;
	}
	
	@PreAuthorize("hasAuthority(ROLE_USER)")
	@PostMapping
	public ResponseEntity<Expense> createExpense (@RequestBody Expense expense,Principal principal){
		Expense createExpense=expenseService.create(expense,principal.getName() );
		return ResponseEntity.ok(createExpense);
	}
	
	@PreAuthorize("hasAuthority(ROLE_USER)")
	@GetMapping
	public ResponseEntity<List<Expense>>getAllExpense(Principal principal){
		List<Expense> expense=expenseService.getAll(principal.getName());
		return ResponseEntity.ok(expense);
		
	}
	
	@PreAuthorize("hasAuthority(ROLE_USER)")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void>deleteExpense(@PathVariable Long id,Principal principal){
		expenseService.delete(id,principal.getName());
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAuthority(ROLE_USER)")
	@GetMapping("/{id}")
	public ResponseEntity<Expense> getExpenseById(@PathVariable Long id,Principal principal){
		Expense expense =expenseService.getById(id, principal.getName());
		return ResponseEntity.ok(expense);
	}
	
	@PreAuthorize("hasAuthority(ROLE_USER)")
	@PutMapping("/{id}")
	public ResponseEntity<Expense>updatedExpense(@PathVariable Long id,@RequestBody Expense expense,Principal principal){
		Expense updatedExpense =expenseService.update(id, expense, principal.getName());
		return ResponseEntity.ok(updatedExpense);
	}
	
	
}
