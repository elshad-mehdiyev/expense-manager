package com.expense.expensemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    private var _categoryModelData = repo.showAllDataFromCategory()
    val categoryModelData: LiveData<List<CategoryModel>> = _categoryModelData

    private var _categoryIncomeModel = repo.showAllIncomeCategoryFromCategoryModel()
    val categoryIncomeModel: LiveData<List<CategoryModel>> = _categoryIncomeModel

    fun insertListToCategory(list: List<CategoryModel>) = viewModelScope.launch {
        repo.insertListToCategory(list)
    }
}