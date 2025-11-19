package com.expensetracker.controller;

import com.expensetracker.model.User;
import com.expensetracker.model.Expense;
import com.expensetracker.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/expenses")
    public List<Expense> getUserExpenses(@RequestParam Long userId) {
        return adminService.getUserExpenses(userId);
    }
}
