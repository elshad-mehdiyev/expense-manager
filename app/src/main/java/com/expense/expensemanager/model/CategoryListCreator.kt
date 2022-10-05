package com.expense.expensemanager.model

import com.expense.expensemanager.R
import javax.inject.Inject

class CategoryListCreator @Inject constructor() {

    fun listOfCategoryImageForExpense(): List<CreateCategoryModel> {
        return listOf(
            CreateCategoryModel(categoryImagePath = R.drawable.category_doctor),
            CreateCategoryModel(categoryImagePath = R.drawable.category_restaurant),
            CreateCategoryModel(categoryImagePath = R.drawable.category_education),
            CreateCategoryModel(categoryImagePath = R.drawable.category_family),
            CreateCategoryModel(categoryImagePath = R.drawable.category_communal),
            CreateCategoryModel(categoryImagePath = R.drawable.category_gift),
            CreateCategoryModel(categoryImagePath = R.drawable.category_public_transport),
            CreateCategoryModel(categoryImagePath = R.drawable.category_workout),
            CreateCategoryModel(categoryImagePath = R.drawable.category_other),
        )
    }
    fun listOfCategoryImageForIncome(): List<CreateCategoryModel> {
        return listOf(
            CreateCategoryModel(categoryImagePath = R.drawable.category_salary),
            CreateCategoryModel(categoryImagePath = R.drawable.category_own_business),
            CreateCategoryModel(categoryImagePath = R.drawable.category_debt),
            CreateCategoryModel(categoryImagePath = R.drawable.category_gift),
            CreateCategoryModel(categoryImagePath = R.drawable.category_other),
        )
    }
}