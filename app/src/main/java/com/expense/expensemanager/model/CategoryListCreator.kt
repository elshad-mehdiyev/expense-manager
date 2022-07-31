package com.expense.expensemanager.model

import com.expense.expensemanager.R
import javax.inject.Inject

class CategoryListCreator @Inject constructor() {

    fun listOfCategoryImageForExpense(): List<CreateCategoryModel> {
        return listOf(
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
            CreateCategoryModel(categoryImagePath = R.drawable.category_add),
        )
    }
    fun listOfCategoryImageForIncome(): List<CreateCategoryModel> {
        return listOf(
            CreateCategoryModel(categoryImagePath = R.drawable.category_salary),
            CreateCategoryModel(categoryImagePath = R.drawable.category_salary),
            CreateCategoryModel(categoryImagePath = R.drawable.category_salary),
            CreateCategoryModel(categoryImagePath = R.drawable.category_salary),
            CreateCategoryModel(categoryImagePath = R.drawable.category_salary),
        )
    }
}