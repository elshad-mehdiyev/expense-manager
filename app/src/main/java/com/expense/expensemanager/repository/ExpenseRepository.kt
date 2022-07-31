package com.expense.expensemanager.repository

import androidx.lifecycle.LiveData
import com.expense.expensemanager.model.CategoryListCreator
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.model.CreateCategoryModel
import com.expense.expensemanager.model.ExpenseModel
import com.expense.expensemanager.room.ExpenseDao
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val dao: ExpenseDao,
    private val categoryListCreator: CategoryListCreator
): ExpenseRepositoryInterface {
    override suspend fun insertData(expenseModel: ExpenseModel) {
        dao.insertData(expenseModel)
    }

    override suspend fun deleteData(expenseModel: ExpenseModel) {
        deleteData(expenseModel)
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


    override suspend fun updateExpenseByCategory(category: String, newValue: Double) {
        dao.updateExpenseByCategory(category, newValue)
    }

    override fun showExpenseByCategoryMonthly(month: String): LiveData<List<ExpenseModel>> {
        return dao.showExpenseByCategoryMonthly(month)
    }

    override fun showAllCategoryImageForExpense(): List<CreateCategoryModel> {
        return categoryListCreator.listOfCategoryImageForExpense()
    }

    override fun showAllCategoryImageForIncome(): List<CreateCategoryModel> {
        return categoryListCreator.listOfCategoryImageForIncome()
    }
}