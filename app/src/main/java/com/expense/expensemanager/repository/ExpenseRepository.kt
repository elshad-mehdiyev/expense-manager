package com.expense.expensemanager.repository

import androidx.lifecycle.LiveData
import com.expense.expensemanager.model.*
import com.expense.expensemanager.room.ExpenseDao
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val dao: ExpenseDao,
    private val categoryListCreator: CategoryListCreator
): ExpenseRepositoryInterface {
    override suspend fun insertData(expenseModel: ExpenseModel) {
        dao.insertData(expenseModel)
    }

    override suspend fun updateExpense(expense: Double?, assign: String, date: String, id: Int,
                                       month: String, income: Double?) {
        dao.updateExpense(expense, assign, date, id, month, income)
    }

    override suspend fun updateByCategory(
        expenseCategory: Double?,
        incomeByCategory: Double?,
        category: String
    ) {
        dao.updateByCategory(expenseCategory, incomeByCategory, category)
    }

    override suspend fun deleteData(expenseModel: ExpenseModel) {
        dao.deleteData(expenseModel)
    }

    override fun showAllData(): LiveData<List<ExpenseModel>> {
        return dao.showAllData()
    }

    override fun showAllExpense(): LiveData<List<ExpenseModel>> {
        return dao.showAllExpense()
    }

    override fun showAllIncome(): LiveData<List<ExpenseModel>> {
        return dao.showAllIncome()
    }

    override fun showDataExpenseMonthly(month: String): LiveData<List<ExpenseModel>> {
        return dao.showExpenseDataMonthly(month)
    }

    override fun showDataIncomeMonthly(month: String): LiveData<List<ExpenseModel>> {
        return dao.showIncomeDataMonthly(month)
    }


    override fun showTotalIncome(): LiveData<Double> {
        return dao.showTotalIncome()
    }

    override fun showTotalExpense(): LiveData<Double> {
        return dao.showTotalExpense()
    }

    override fun showMonthlyTotalIncome(month: String): LiveData<Double> {
        return dao.showMonthlyTotalIncome(month)
    }

    override fun showMonthlyTotalExpense(month: String): LiveData<Double> {
        return dao.showMonthlyTotalExpense(month)
    }

    /**
     * CategoryModel  table
     */
    override suspend fun insertToCategoryModel(categoryModel: CategoryModel) {
        dao.insertDataToCategoryModel(categoryModel)
    }

    override suspend fun deleteFromCategoryModel(categoryModel: CategoryModel) {
        dao.deleteFromCategory(categoryModel)
    }

    override fun showAllDataFromCategory(): LiveData<List<CategoryModel>> {
        return dao.showAllDataFromCategoryModel()
    }

    override fun showAllIncomeCategoryFromCategoryModel(): LiveData<List<CategoryModel>> {
        return dao.showAllIncomeCategoryFromCategoryName()
    }

    override suspend fun insertListToCategory(list: List<CategoryModel>) {
        dao.insertListToCategory(list)
    }
    override fun showAllCategoryImageForExpense(): List<CreateCategoryModel> {
        return categoryListCreator.listOfCategoryImageForExpense()
    }

    override fun showAllCategoryImageForIncome(): List<CreateCategoryModel> {
        return categoryListCreator.listOfCategoryImageForIncome()
    }
    /**
     * Saving  Goal  Table
     */
    override suspend fun insertToSavingDB(createSavingGoalModel: CreateSavingGoalModel) {
        dao.insertToSavingGoal(createSavingGoalModel)
    }

    override fun showAllSavingGoals(): LiveData<List<CreateSavingGoalModel>> {
        return dao.showAllSavingData()
    }

    override suspend fun deleteFromSavingDB(createSavingGoalModel: CreateSavingGoalModel) {
        dao.deleteFromSavingGoals(createSavingGoalModel)
    }
}