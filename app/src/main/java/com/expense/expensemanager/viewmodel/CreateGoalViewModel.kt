package com.expense.expensemanager.viewmodel


import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.expensemanager.constant.Resource
import com.expense.expensemanager.model.CreateSavingGoalModel
import com.expense.expensemanager.repository.ExpenseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGoalViewModel @Inject constructor(
    private val repo: ExpenseRepositoryInterface
): ViewModel() {

    private fun insertToGoalDB(createSavingGoalModel: CreateSavingGoalModel) = viewModelScope.launch {
        repo.insertToSavingDB(createSavingGoalModel)
    }
    private var insertArtMsg = MutableLiveData<Resource<CreateSavingGoalModel>>()
    val insertArtMessage : LiveData<Resource<CreateSavingGoalModel>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<CreateSavingGoalModel>>()
    }
    fun makeGoal(name: String, amount: String, note: String, image: Bitmap) {
        if(name.isEmpty() || amount.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name, amount", null))
            return
        }
        val amountToDouble = try {
            amount.toDouble()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Amount should be decimal",null))
            return
        }
        val noteGoal = note.ifEmpty {
            null
        }
        val goalModel = CreateSavingGoalModel(savingName = name, savingAmount = amountToDouble,
            savingNote = noteGoal, savingImage = image)
        insertToGoalDB(goalModel)
        insertArtMsg.postValue(Resource.success(CreateSavingGoalModel()))
    }
    fun makeSmallerBitmap(image: Bitmap, maximumSize: Int): Bitmap {
        var width = image.width
        var height = image.height
        val bitmapRatio: Double = width.toDouble() /height.toDouble()
        when {
            bitmapRatio > 1 -> {
                //Landscape
                width = maximumSize
                val scaledHeight = width / bitmapRatio
                height = scaledHeight.toInt()
            }
            bitmapRatio == 1.0 -> {
                width = maximumSize
                height = width
            }
            else -> {
                // Portrait
                height = maximumSize
                val scaledWidth = height * bitmapRatio
                width = scaledWidth.toInt()
            }
        }
        return Bitmap.createScaledBitmap(image,width, height, true)

    }

}