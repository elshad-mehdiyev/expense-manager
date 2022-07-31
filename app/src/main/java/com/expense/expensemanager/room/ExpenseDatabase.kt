package com.expense.expensemanager.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.model.ExpenseModel

@Database(entities = [ExpenseModel::class,CategoryModel::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun getDao(): ExpenseDao
}