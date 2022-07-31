package com.expense.expensemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseModel(
    val assign: String? = null,
    val expense: Double? = null,
    val income: Double? = null,
    val iconPath: Int? = null,
    val date: String? = null,
    val month: String? = null,
    val category: String? = null,
    val expenseSumByCategory: Double? = null,
    val incomeSumByCategory: Double? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)