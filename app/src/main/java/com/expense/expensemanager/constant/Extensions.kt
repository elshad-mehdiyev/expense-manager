package com.expense.expensemanager.constant

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