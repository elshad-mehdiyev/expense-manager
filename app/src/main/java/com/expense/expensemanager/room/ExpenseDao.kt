package com.expense.expensemanager.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.model.CreateSavingGoalModel
import com.expense.expensemanager.model.ExpenseModel

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertData(expenseModel: ExpenseModel)
    @Query("update ExpenseModel set expense =:expense, assign =:assign, date =:date, month =:month, income =:income where id =:id")
    suspend fun updateExpense(expense: Double?, assign: String, date: String,
                              id: Int, month: String, income: Double?)
    @Query("update ExpenseModel set expenseByCategory =expenseByCategory + :expenseCategory, incomeByCategory =incomeByCategory + :incomeByCategory where category =:category")
    suspend fun updateByCategory(expenseCategory: Double?, incomeByCategory: Double?, category: String)
    @Query("SELECT * FROM ExpenseModel")
    fun showAllData(): LiveData<List<ExpenseModel>>
    @Query("SELECT SUM(expense) FROM ExpenseModel")
    fun showTotalExpense(): LiveData<Double>
    @Query("SELECT SUM(income) FROM ExpenseModel")
    fun showTotalIncome(): LiveData<Double>
    @Query("SELECT SUM(expense) FROM ExpenseModel WHERE month =:month")
    fun showMonthlyTotalExpense(month: String): LiveData<Double>
    @Query("SELECT SUM(income) FROM ExpenseModel WHERE month =:month")
    fun showMonthlyTotalIncome(month: String): LiveData<Double>
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

    // Saving  Goal  Model
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToSavingGoal(createSavingGoalModel: CreateSavingGoalModel)
    @Query("SELECT * FROM CreateSavingGoalModel")
    fun showAllSavingData(): LiveData<List<CreateSavingGoalModel>>
    @Delete
    suspend fun deleteFromSavingGoals(createSavingGoalModel: CreateSavingGoalModel)
}
