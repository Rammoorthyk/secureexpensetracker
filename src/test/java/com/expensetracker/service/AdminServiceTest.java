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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    private User user1;
    private User user2;
    private Expense expense1;
    private Expense expense2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("maharaja");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("alex");

        expense1 = new Expense();
        expense1.setId(100L);
        expense1.setDescription("Lunch");
        expense1.setAmount(BigDecimal.valueOf(200.0));
        expense1.setDate(LocalDate.parse("2025-10-10"));
        expense1.setUser(user1);

        expense2 = new Expense();
        expense2.setId(101L);
        expense2.setDescription("Dinner");
        expense2.setAmount(BigDecimal.valueOf(300.0));
        expense2.setDate(LocalDate.parse("2025-10-11"));
        expense2.setUser(user1);
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = adminService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("maharaja", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserExpenses() {
        List<Expense> mockExpenses = Arrays.asList(expense1, expense2);
        when(expenseRepository.findByUserId(1L)).thenReturn(mockExpenses);

        List<Expense> result = adminService.getUserExpenses(1L);

        assertEquals(2, result.size());
        assertEquals("Dinner", result.get(1).getDescription());
        verify(expenseRepository, times(1)).findByUserId(1L);
    }
}
