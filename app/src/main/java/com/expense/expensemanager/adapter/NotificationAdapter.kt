package com.expense.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.databinding.NotificationItemBinding
import com.expense.expensemanager.model.ExpenseModel

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {
    inner class NotificationHolder(var binding: NotificationItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<ExpenseModel>() {
        override fun areItemsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem == newItem
        }
    }
    private val asyncList = AsyncListDiffer(this, diffUtil)
    var notificationList: List<ExpenseModel>
    get() = asyncList.currentList
    set(value) = asyncList.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.binding.notification = notificationList[position]
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}