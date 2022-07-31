package com.expense.expensemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.expense.expensemanager.model.ExpenseModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesPageViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    private var _expenseMonthly = MutableLiveData<List<ExpenseModel>>()
    var expenseMonthly: LiveData<List<ExpenseModel>> = _expenseMonthly

    fun showExpenseDataMonthly(month: String) {
        expenseMonthly = repo.showDataExpenseMonthly(month)
    }
}