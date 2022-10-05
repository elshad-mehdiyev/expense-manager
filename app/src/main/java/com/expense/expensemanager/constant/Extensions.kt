package com.expense.expensemanager.constant

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter


fun ImageView.setImage(id: Int) {
        this.setImageResource(id)
}

@BindingAdapter("android:setImage")
fun set(imageView: ImageView, id: Int?) {
    id?.let {
        imageView.setImage(id)
    }
}

fun ImageView.setBit(bitmap: Bitmap) {
    this.setImageBitmap(bitmap)
}

@BindingAdapter("android:setBitmap")
fun setBitmap(imageView: ImageView, bitmap: Bitmap) {
    imageView.setBit(bitmap)
}