package com.expense.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTextViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    fun updateToNewExpense(expense: Double?, assign: String, date: String,
                           id: Int, month: String, income: Double?) = viewModelScope.launch {
        repo.updateExpense(expense, assign, date, id, month, income)
    }
}