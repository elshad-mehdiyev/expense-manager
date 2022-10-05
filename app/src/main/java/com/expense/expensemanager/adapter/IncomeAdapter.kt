package com.expense.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.databinding.IncomePageItemBinding
import com.expense.expensemanager.model.ExpenseModel

class IncomeAdapter: RecyclerView.Adapter<IncomeAdapter.IncomeHolder>() {
    inner class IncomeHolder(var binding: IncomePageItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<ExpenseModel>() {
        override fun areItemsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem == newItem
        }
    }
    private val list = AsyncListDiffer(this, diffUtil)

    var incomeList: List<ExpenseModel>
    get() = list.currentList
    set(value) = list.submitList(value)

    private var onItemClickListener: ((ExpenseModel) -> Unit)? = null

    fun setOnClickListener(listener: (ExpenseModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeHolder {
        val binding = IncomePageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IncomeHolder(binding)
    }

    override fun onBindViewHolder(holder: IncomeHolder, position: Int) {
        holder.binding.income = incomeList[position]
        holder.binding.incomeItemLayout.setOnClickListener {
            onItemClickListener?.let {
                it(incomeList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }
}