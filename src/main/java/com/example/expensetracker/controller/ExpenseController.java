
package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expenses")
    public String listExpenses(Model model, Principal principal) {
        String username = principal.getName();

        List<Expense> expenses =
                expenseRepository.findByUsernameOrderByExpenseDateDescIdDesc(username);

        BigDecimal totalSpent = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        YearMonth currentMonth = YearMonth.now();

        BigDecimal monthlySpent = expenses.stream()
                .filter(expense -> expense.getExpenseDate() != null
                        && YearMonth.from(expense.getExpenseDate())
                        .equals(currentMonth))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> categoryTotals = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        LinkedHashMap::new,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Expense::getAmount,
                                BigDecimal::add
                        )
                ));

        model.addAttribute("expenses", expenses);
        model.addAttribute("totalSpent", totalSpent);
        model.addAttribute("monthlySpent", monthlySpent);
        model.addAttribute("categoryTotals", categoryTotals);
        model.addAttribute("expenseCount", expenses.size());
        model.addAttribute("currentMonth", currentMonth);

        return "expenses/list";
    }

    @GetMapping("/expenses/new")
    public String newExpense(Model model) {
        Expense expense = new Expense();
        expense.setExpenseDate(LocalDate.now());

        model.addAttribute("expense", expense);
        model.addAttribute("pageTitle", "Add Expense");

        return "expenses/form";
    }

    @PostMapping("/expenses")
    public String createExpense(
            @Valid @ModelAttribute("expense") Expense expense,
            BindingResult result,
            Principal principal,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add Expense");
            return "expenses/form";
        }

        expense.setId(null);
        expense.setUsername(principal.getName());

        expenseRepository.save(expense);

        return "redirect:/expenses";
    }

    @GetMapping("/expenses/{id}/edit")
    public String editExpense(
            @PathVariable Long id,
            Principal principal,
            Model model) {

        Optional<Expense> optionalExpense =
                expenseRepository.findByIdAndUsername(id, principal.getName());

        if (optionalExpense.isEmpty()) {
            return "redirect:/expenses";
        }

        model.addAttribute("expense", optionalExpense.get());
        model.addAttribute("pageTitle", "Edit Expense");

        return "expenses/form";
    }

    @PostMapping("/expenses/{id}")
    public String updateExpense(
            @PathVariable Long id,
            @Valid @ModelAttribute("expense") Expense expense,
            BindingResult result,
            Principal principal,
            Model model) {

        Optional<Expense> optionalExpense =
                expenseRepository.findByIdAndUsername(id, principal.getName());

        if (optionalExpense.isEmpty()) {
            return "redirect:/expenses";
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Expense");
            return "expenses/form";
        }

        expense.setId(id);
        expense.setUsername(principal.getName());

        expenseRepository.save(expense);

        return "redirect:/expenses";
    }

    @PostMapping("/expenses/{id}/delete")
    public String deleteExpense(
            @PathVariable Long id,
            Principal principal) {

        Optional<Expense> optionalExpense =
                expenseRepository.findByIdAndUsername(id, principal.getName());

        optionalExpense.ifPresent(expenseRepository::delete);

        return "redirect:/expenses";
    }
}
