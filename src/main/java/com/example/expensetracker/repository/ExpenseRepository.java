
package com.example.expensetracker.repository;

import com.example.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUsernameOrderByExpenseDateDescIdDesc(String username);

    Optional<Expense> findByIdAndUsername(Long id, String username);
}
