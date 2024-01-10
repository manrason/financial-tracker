package com.manrason.financialtracker.controller;

import com.manrason.financialtracker.model.Expense;
import com.manrason.financialtracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping(path = "about")
    public String selfDescription() {
        return "Welcome to expense-tracking version 1.0.0. New features will be added soon...";
    }

    @GetMapping()
    public List<Expense> getExpenseByIdorName(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name) {
        List<Expense> result = new ArrayList<>();
        try {
            if ((id == null) && (name == null)) {
                result = expenseService.getExpenses();
            }
            else if (name == null) {
                result.add(expenseService.getExpenseById(id));
            }
            else {
                result.add(expenseService.getExpenseByName(name));
            }
        } catch ( Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return result;
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        try {
            return expenseService.addExpense(expense);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Invalid date format, use YYYY-MM-DD format", e);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "{expenseId}")
    public void deleteExpenseById(
            @PathVariable("expenseId") Long expenseId
    ) {
        try {
            expenseService.deleteExpenseById(expenseId);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(path = "{oldExpenseId}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable("oldExpenseId") Long oldExpenseId,
            @RequestBody Expense expense
    ) {
        try {
            return expenseService.updateoldExpense(oldExpenseId, expense);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
