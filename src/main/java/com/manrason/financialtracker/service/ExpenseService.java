package com.manrason.financialtracker.service;

import com.manrason.financialtracker.model.Expense;
import com.manrason.financialtracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> getExpenses(){
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) throws Exception {
        Optional<Expense> _expense = expenseRepository.findById(id);

        if (_expense.isEmpty()) {
            throw new Exception("Expense does not exist...");
        }
        return _expense.get();
    }

    public Expense getExpenseByName(String name) throws Exception {
        Optional<Expense> _expense = expenseRepository.findExpenseByName(name);

        if (_expense.isEmpty()) {
            throw new Exception("Expense does not exist...");
        }

        return _expense.get();
    }

    public Expense addExpense(Expense expense) throws Exception {
        Optional<Expense> _expense = expenseRepository.findExpenseByName(expense.getName());
        if (_expense.isPresent()) {
            throw new Exception("Expense already exists");
        }

        expenseRepository.save(expense);
        return expense;
    }

    public void deleteExpenseById(Long expenseId) {
        Optional<Expense> _expense = expenseRepository.findById(expenseId);

        if (_expense.isPresent()) {
            System.out.println("Deleting expense id " + expenseId);
            expenseRepository.deleteById(expenseId);
        }
        return;
    }

    public ResponseEntity<Expense> updateoldExpense(Long oldExpenseId, Expense expense) throws Exception {
        Optional<Expense> _expense = expenseRepository.findById(oldExpenseId);

        if (_expense.isEmpty()) {
            throw new Exception("Expense does not exist...");
        }

        _expense.get().setName(expense.getName());
        _expense.get().setAmount(expense.getAmount());
        _expense.get().setComments(expense.getComments());
        _expense.get().setCreationDate(expense.getCreationDate());
        Expense updatedExpense = expenseRepository.save(_expense.get());
        return ResponseEntity.ok(updatedExpense);
    }
}
