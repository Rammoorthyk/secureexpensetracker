package com.expensetracker.repository;

import org.springframework.data.jpa.repository.*;
import com.expensetracker.model.User;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUsername(String username);

}
