package com.expense.expensemanager.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CreateSavingGoalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val savingName: String? = null,
    val savingAmount: Double? = null,
    val savingNote: String? = null,
    val savingImage: Bitmap? = null
)
