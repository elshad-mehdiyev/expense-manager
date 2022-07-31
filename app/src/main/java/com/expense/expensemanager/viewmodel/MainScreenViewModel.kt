package com.expense.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    val totalIncome = repo.showTotalIncome()

    val totalExpense = repo.showTotalExpense()
}