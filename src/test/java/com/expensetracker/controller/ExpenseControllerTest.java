package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private Principal principal;

    @InjectMocks
    private ExpenseController expenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(principal.getName()).thenReturn("maharaja");
    }

    @Test
    void testCreateExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Lunch");

        when(expenseService.create(expense, "maharaja")).thenReturn(expense);

        ResponseEntity<Expense> response = expenseController.createExpense(expense, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lunch", response.getBody().getDescription());
        verify(expenseService, times(1)).create(expense, "maharaja");
    }

    @Test
    void testGetAllExpense() {
        Expense e1 = new Expense();
        Expense e2 = new Expense();
        List<Expense> list = Arrays.asList(e1, e2);

        when(expenseService.getAll("maharaja")).thenReturn(list);

        ResponseEntity<List<Expense>> response = expenseController.getAllExpense(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(expenseService, times(1)).getAll("maharaja");
    }

    @Test
    void testGetExpenseById() {
        Expense expense = new Expense();
        expense.setId(10L);
        expense.setDescription("Travel");

        when(expenseService.getById(10L, "maharaja")).thenReturn(expense);

        ResponseEntity<Expense> response = expenseController.getExpenseById(10L, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Travel", response.getBody().getDescription());
        verify(expenseService, times(1)).getById(10L, "maharaja");
    }

    @Test
    void testUpdateExpense() {
        Expense expense = new Expense();
        expense.setDescription("Dinner");
        Expense updatedExpense = new Expense();
        updatedExpense.setDescription("Updated Dinner");

        when(expenseService.update(5L, expense, "maharaja")).thenReturn(updatedExpense);

        ResponseEntity<Expense> response = expenseController.updatedExpense(5L, expense, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Dinner", response.getBody().getDescription());
        verify(expenseService, times(1)).update(5L, expense, "maharaja");
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseService).delete(3L, "maharaja");

        ResponseEntity<Void> response = expenseController.deleteExpense(3L, principal);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(expenseService, times(1)).delete(3L, "maharaja");
    }
}
