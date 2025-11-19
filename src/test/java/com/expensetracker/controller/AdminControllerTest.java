package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Role;
import com.expensetracker.model.User;
import com.expensetracker.service.AdminService;
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

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private User user1;
    private User user2;
    private Expense expense1;
    private Expense expense2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User(1L, "Maharaja", "pass123", Role.ROLE_USER);
        user2 = new User(2L, "Admin", "admin123", Role.ROLE_ADMIN);

        expense1 = new Expense();
        expense1.setId(100L);
        expense1.setDescription("Food");
        expense1.setAmount(BigDecimal.valueOf(250.50));
        expense1.setDate(LocalDate.of(2025, 11, 1));
        expense1.setUser(user1);

        expense2 = new Expense();
        expense2.setId(101L);
        expense2.setDescription("Travel");
        expense2.setAmount(BigDecimal.valueOf(500.00));
        expense2.setDate(LocalDate.of(2025, 11, 2));
        expense2.setUser(user2);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user1, user2);
        when(adminService.getAllUsers()).thenReturn(users);

        List<User> result = adminController.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Maharaja", result.get(0).getUsername());
        verify(adminService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserExpenses() {
        List<Expense> expenses = Arrays.asList(expense1);
        when(adminService.getUserExpenses(1L)).thenReturn(expenses);

        List<Expense> result = adminController.getUserExpenses(1L);

        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).getDescription());
        verify(adminService, times(1)).getUserExpenses(1L);
    }
}
