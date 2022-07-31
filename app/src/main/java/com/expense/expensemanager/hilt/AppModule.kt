package com.expense.expensemanager.hilt

import android.content.Context
import androidx.room.Room
import com.expense.expensemanager.constant.Constant.DB_NAME
import com.expense.expensemanager.model.CategoryListCreator
import com.expense.expensemanager.repository.ExpenseRepository
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import com.expense.expensemanager.room.ExpenseDao
import com.expense.expensemanager.room.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ExpenseDatabase = Room.databaseBuilder(
        context,
        ExpenseDatabase::class.java,
        DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(
        expenseDatabase: ExpenseDatabase
    ) = expenseDatabase.getDao()

    @Singleton
    @Provides
    fun provideRepositoryInterface(
        dao: ExpenseDao,
        categoryListCreator: CategoryListCreator
    ) = ExpenseRepository(dao, categoryListCreator) as ExpenseRepositoryInterface
}