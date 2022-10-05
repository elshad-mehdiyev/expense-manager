package com.expense.expensemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryPageViewModel @Inject constructor(
    repo: ExpenseRepositoryInterface
): ViewModel() {

    private var _expenseByCategoryMonthly = MutableLiveData<Double>()
    var expenseByCategoryMonthly: LiveData<Double> = _expenseByCategoryMonthly

    val showAllData = repo.showAllData()

}