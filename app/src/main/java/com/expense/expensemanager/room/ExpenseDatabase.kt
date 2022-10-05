package com.expense.expensemanager.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.model.CreateSavingGoalModel
import com.expense.expensemanager.model.ExpenseModel

@Database(
    entities = [ExpenseModel::class, CategoryModel::class, CreateSavingGoalModel::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun getDao(): ExpenseDao
}