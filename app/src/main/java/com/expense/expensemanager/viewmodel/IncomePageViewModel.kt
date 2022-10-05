package com.expense.expensemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.expensemanager.model.ExpenseModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomePageViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    private var _incomeMonthly = MutableLiveData<List<ExpenseModel>>()
    var incomeMonthly: LiveData<List<ExpenseModel>> = _incomeMonthly

    fun showIncomeDataMonthly(month: String) {
        incomeMonthly = repo.showDataIncomeMonthly(month)
    }
    fun saveIncomeItem(expenseModel: ExpenseModel) = viewModelScope.launch {
        repo.insertData(expenseModel)
    }
    fun deleteIncomeItem(expenseModel: ExpenseModel) = viewModelScope.launch {
        repo.deleteData(expenseModel)
    }
}