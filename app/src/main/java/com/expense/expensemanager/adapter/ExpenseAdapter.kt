package com.expense.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.databinding.ExpensePageItemBinding
import com.expense.expensemanager.model.ExpenseModel
import com.expense.expensemanager.ui.ExpensesPageDirections

class ExpenseAdapter: RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>() {

    inner class ExpenseHolder(var binding: ExpensePageItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<ExpenseModel>() {
        override fun areItemsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem == newItem
        }
    }
    private val list = AsyncListDiffer(this,diffUtil)

    var expenseList: List<ExpenseModel>
    get() = list.currentList
    set(value) = list.submitList(value)

    private var onItemClickListener: ((ExpenseModel) -> Unit)? = null

    fun setOnClickListener(listener: (ExpenseModel) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val binding = ExpensePageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        holder.binding.expense = expenseList[position]
        holder.binding.expenseItemLayout.setOnClickListener {
            onItemClickListener?.let {
                it(expenseList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }
}