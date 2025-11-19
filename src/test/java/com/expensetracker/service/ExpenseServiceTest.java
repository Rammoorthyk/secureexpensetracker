package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private User user;
    private Expense expense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("maharaja");

        expense = new Expense();
        expense.setId(10L);
        expense.setDescription("Lunch");
        expense.setAmount(BigDecimal.valueOf(200.0)); // ✅ FIXED
        expense.setDate(LocalDate.parse("2025-10-10")); // ✅ FIXED
        expense.setUser(user);
    }

    @Test
    void testCreateExpenseSuccess() {
        when(userRepository.findByUsername("maharaja")).thenReturn(Optional.of(user));
        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense result = expenseService.create(expense, "maharaja");

        assertNotNull(result);
        assertEquals("Lunch", result.getDescription());
        verify(userRepository, times(1)).findByUsername("maharaja");
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void testCreateExpenseUserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                expenseService.create(expense, "unknown")
        );

        assertTrue(ex.getMessage().contains("user not found"));
    }

    @Test
    void testGetAllExpenses() {
        when(userRepository.findByUsername("maharaja")).thenReturn(Optional.of(user));
        when(expenseRepository.findByUserId(1L)).thenReturn(Arrays.asList(expense));

        List<Expense> result = expenseService.getAll("maharaja");

        assertEquals(1, result.size());
        verify(expenseRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetExpenseByIdSuccess() {
        when(expenseRepository.findById(10L)).thenReturn(Optional.of(expense));

        Expense result = expenseService.getById(10L, "maharaja");

        assertEquals("Lunch", result.getDescription());
        verify(expenseRepository, times(1)).findById(10L);
    }

    @Test
    void testGetExpenseByIdUnauthorized() {
        User otherUser = new User();
        otherUser.setUsername("hacker");
        expense.setUser(otherUser);

        when(expenseRepository.findById(10L)).thenReturn(Optional.of(expense));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                expenseService.getById(10L, "maharaja")
        );

        assertEquals("Unauthorized access", ex.getMessage());
    }

    @Test
    void testDeleteExpense() {
        when(expenseRepository.findById(10L)).thenReturn(Optional.of(expense));

        expenseService.delete(10L, "maharaja");

        verify(expenseRepository, times(1)).delete(expense);
    }

    @Test
    void testUpdateExpense() {
        Expense newExpense = new Expense();
        newExpense.setDescription("Updated Lunch");
        newExpense.setAmount(BigDecimal.valueOf(250.0)); // ✅ FIXED
        newExpense.setDate(LocalDate.parse("2025-10-11")); // ✅ FIXED

        when(expenseRepository.findById(10L)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        Expense result = expenseService.update(10L, newExpense, "maharaja");

        assertNotNull(result);
        assertEquals("Updated Lunch", result.getDescription());
        verify(expenseRepository, times(1)).save(expense);
    }
}
