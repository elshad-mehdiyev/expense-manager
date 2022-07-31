package com.expense.expensemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryModel(
    val categoryName: String? = null,
    val incomeCategoryName: String? = null,
    val imagePath: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)