package com.expense.expensemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.expense.expensemanager.model.ExpenseModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryPageViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    private var _expenseByCategoryMonthly = MutableLiveData<List<ExpenseModel>>()
    var expenseByCategoryMonthly: LiveData<List<ExpenseModel>> = _expenseByCategoryMonthly

    fun showExpenseByCategoryMonthly(month: String) {
        expenseByCategoryMonthly = repo.showExpenseByCategoryMonthly(month)
    }
}