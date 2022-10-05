package com.expense.expensemanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ExpenseModel(
    val assign: String? = null,
    val expense: Double? = 0.0,
    val income: Double? = 0.0,
    val iconPath: Int? = null,
    val date: String? = null,
    val month: String? = null,
    val category: String? = null,
    val expenseByCategory: Double? = null,
    val incomeByCategory: Double? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
): Parcelable