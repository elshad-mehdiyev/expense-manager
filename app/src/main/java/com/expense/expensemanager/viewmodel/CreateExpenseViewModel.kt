package com.expense.expensemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.expensemanager.constant.Resource
import com.expense.expensemanager.model.ExpenseModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {


    private var _insertMessage = MutableLiveData<Resource<ExpenseModel>>()
    val insertMessage : LiveData<Resource<ExpenseModel>> = _insertMessage

    fun resetInsertMsg() {
        _insertMessage = MutableLiveData<Resource<ExpenseModel>>()
    }
    fun saveExpense(amount: String, assign: String, date: String,
                    iconPath: Int? = null, category: String, incomeByCategory: Double? = null) {
        if (amount.isEmpty()) {
            _insertMessage.postValue(Resource.error("Enter  amount",null))
            return
        }
        val amountToDouble = try {
            -BigDecimal(amount.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        } catch (e: Exception) {
            _insertMessage.postValue(Resource.error("enter  valid  amount", null))
            return
        }
        val month = date.split("/").toTypedArray()[0]
        val expenseModel = ExpenseModel(assign = assign, expense = amountToDouble, date = date,
            month = month, iconPath = iconPath, category = category, income = null)
        insertToDb(expenseModel)
        updateExpenseByCategory(amountToDouble, incomeByCategory, category)
        _insertMessage.postValue(Resource.success(expenseModel))
    }
    private fun insertToDb(expenseModel: ExpenseModel) {
        viewModelScope.launch {
            repo.insertData(expenseModel)
        }
    }
    private fun updateExpenseByCategory(expenseCategory: Double?, incomeByCategory: Double?,
                                category: String) = viewModelScope.launch {
        repo.updateByCategory(expenseCategory, incomeByCategory, category)
    }
}