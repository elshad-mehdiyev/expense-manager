package com.expense.expensemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavingPageViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    val goalList = repo.showAllSavingGoals()

    val totalIncome = repo.showTotalIncome()

    val totalExpense = repo.showTotalExpense()

    private var _showMonthlyTotalIncome = MutableLiveData<Double>()
    var showMonthlyTotalIncome: LiveData<Double> = _showMonthlyTotalIncome

    private var _showMonthlyTotalExpense = MutableLiveData<Double>()
    var showMonthlyTotalExpense: LiveData<Double> = _showMonthlyTotalExpense

    fun showMonthlyIncome(month: String) {
        showMonthlyTotalIncome = repo.showMonthlyTotalIncome(month)
    }
    fun showMonthlyExpense(month: String) {
        showMonthlyTotalExpense = repo.showMonthlyTotalExpense(month)
    }

}