package com.expense.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCategoryListViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    val createCategoryImageForExpense = repo.showAllCategoryImageForExpense()

    val createCategoryImageForIncome = repo.showAllCategoryImageForIncome()

    fun insertDataToCategoryModel(categoryModel: CategoryModel) = viewModelScope.launch {
        repo.insertToCategoryModel(categoryModel)
    }
}