package com.expensetracker.repository;
import org.springframework.data.jpa.repository.*;
import com.expensetracker.model.Expense;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
	List<Expense> findByUserId(Long userId);

}
