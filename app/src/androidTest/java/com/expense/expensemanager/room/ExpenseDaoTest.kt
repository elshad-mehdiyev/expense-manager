package com.expense.expensemanager.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.expense.expensemanager.getOrAwaitValue
import com.expense.expensemanager.model.ExpenseModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class ExpenseDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: ExpenseDatabase
    private lateinit var dao: ExpenseDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.getDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_working_return_true() = runTest {
        val expenseModel = ExpenseModel(
            "assign",25.5,340.0,2,"13/25/255",1)
        dao.insertData(expenseModel)

        val data = dao.showAllData().getOrAwaitValue()

        assertThat(data).contains(expenseModel)
    }
    @Test
    fun delete_working_return_true() = runTest {
        val expenseModel = ExpenseModel(
            "assign",25.5,340.0,2,"13/25/255",1)
        dao.insertData(expenseModel)
        dao.deleteData(expenseModel)
        val data = dao.showAllData().getOrAwaitValue()

        assertThat(data).doesNotContain(expenseModel)
    }
    @Test
    fun sum_total_income_equal_return_true() = runTest {
        val expenseModel = ExpenseModel(
            "assign",25.5,340.0,2,"13/25/255",1)
        val expenseModel1 = ExpenseModel(
            "assign",25.5,140.0,2,"13/25/255",2)
        val expenseModel2 = ExpenseModel(
            "assign",25.5,40.0,2,"13/25/255",3)
        dao.insertData(expenseModel)
        dao.insertData(expenseModel1)
        dao.insertData(expenseModel2)
        val sum = dao.showTotalIncome().getOrAwaitValue()
        assertThat(sum).isEqualTo(520.0)
    }
    @Test
    fun sum_total_expense_equal_return_true() = runTest {
        val expenseModel = ExpenseModel(
            "assign",35.5,340.0,2,"13/25/255",1)
        val expenseModel1 = ExpenseModel(
            "assign",25.5,140.0,2,"13/25/255",2)
        val expenseModel2 = ExpenseModel(
            "assign",25.0,40.0,2,"13/25/255",3)
        dao.insertData(expenseModel)
        dao.insertData(expenseModel1)
        dao.insertData(expenseModel2)
        val sum = dao.showTotalExpense().getOrAwaitValue()
        assertThat(sum).isEqualTo(86)
    }
}