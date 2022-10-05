package com.expense.expensemanager.repository

import androidx.lifecycle.LiveData
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.model.CreateCategoryModel
import com.expense.expensemanager.model.CreateSavingGoalModel
import com.expense.expensemanager.model.ExpenseModel

interface ExpenseRepositoryInterface {

    suspend fun insertData(expenseModel: ExpenseModel)
    suspend fun updateExpense(expense: Double?, assign: String, date: String, id: Int,
                              month: String, income: Double?)
    suspend fun updateByCategory(expenseCategory: Double?, incomeByCategory: Double?, category: String)
    suspend fun deleteData(expenseModel: ExpenseModel)
    fun showAllData(): LiveData<List<ExpenseModel>>
    fun showAllExpense(): LiveData<List<ExpenseModel>>
    fun showAllIncome(): LiveData<List<ExpenseModel>>
    fun showDataExpenseMonthly(month: String): LiveData<List<ExpenseModel>>
    fun showDataIncomeMonthly(month: String): LiveData<List<ExpenseModel>>
    fun showTotalIncome(): LiveData<Double>
    fun showTotalExpense(): LiveData<Double>
    fun showMonthlyTotalIncome(month: String): LiveData<Double>
    fun showMonthlyTotalExpense(month: String): LiveData<Double>
    // From  CategoryModel
    suspend fun insertToCategoryModel(categoryModel: CategoryModel)
    suspend fun deleteFromCategoryModel(categoryModel: CategoryModel)
    fun showAllDataFromCategory(): LiveData<List<CategoryModel>>
    fun showAllIncomeCategoryFromCategoryModel(): LiveData<List<CategoryModel>>
    suspend fun insertListToCategory(list: List<CategoryModel>)
    //From  CreateCategory
    fun showAllCategoryImageForExpense(): List<CreateCategoryModel>
    fun showAllCategoryImageForIncome(): List<CreateCategoryModel>
    //From  CreateSavingGoal
    suspend fun insertToSavingDB(createSavingGoalModel: CreateSavingGoalModel)
    fun showAllSavingGoals(): LiveData<List<CreateSavingGoalModel>>
    suspend fun deleteFromSavingDB(createSavingGoalModel: CreateSavingGoalModel)

}