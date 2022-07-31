package com.expense.expensemanager.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.model.CreateCategoryModel
import com.expense.expensemanager.model.ExpenseModel

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(expenseModel: ExpenseModel)
    @Query("SELECT * FROM ExpenseModel")
    fun showAllData(): LiveData<List<ExpenseModel>>
    @Query("SELECT SUM(expense) FROM ExpenseModel")
    fun showTotalExpense(): LiveData<Double>
    @Query("SELECT SUM(income) FROM ExpenseModel")
    fun showTotalIncome(): LiveData<Double>
    @Delete
    suspend fun deleteData(expenseModel: ExpenseModel)
    @Query("select * from ExpenseModel where  income is null")
    fun showAllExpense(): LiveData<List<ExpenseModel>>
    @Query("select * from ExpenseModel where  expense is null")
    fun showAllIncome(): LiveData<List<ExpenseModel>>
    @Query("select * from ExpenseModel where month =:month and income is null")
    fun showExpenseDataMonthly(month: String): LiveData<List<ExpenseModel>>
    @Query("select * from ExpenseModel where month =:month and expense is null")
    fun showIncomeDataMonthly(month: String): LiveData<List<ExpenseModel>>
    @Query("update ExpenseModel set expenseSumByCategory = expenseSumByCategory + :newValue where category =:category")
    suspend fun updateExpenseByCategory(category: String, newValue: Double)
    @Query("update ExpenseModel set incomeSumByCategory = incomeSumByCategory + :newValue where category =:category")
    suspend fun updateIncomeByCategory(category: String, newValue: Double)
    @Query("select * from ExpenseModel where month =:month and incomeSumByCategory is null")
    fun showExpenseByCategoryMonthly(month: String): LiveData<List<ExpenseModel>>
    @Query("select * from ExpenseModel where month =:month and expenseSumByCategory is null")
    fun showIncomeByCategoryMonthly(month: String): LiveData<List<ExpenseModel>>
    //table name = categoryModel
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataToCategoryModel(categoryModel: CategoryModel)
    @Query("select * from CategoryModel where incomeCategoryName is null")
    fun showAllDataFromCategoryModel(): LiveData<List<CategoryModel>>
    @Query("select * from CategoryModel where categoryName is null")
    fun showAllIncomeCategoryFromCategoryName(): LiveData<List<CategoryModel>>
    @Delete
    suspend fun deleteFromCategory(categoryModel: CategoryModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListToCategory(list: List<CategoryModel>)
}
