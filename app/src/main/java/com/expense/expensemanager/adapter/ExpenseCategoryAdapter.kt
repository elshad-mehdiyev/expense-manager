package com.expense.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.databinding.ExpenseCategoryItemBinding
import com.expense.expensemanager.model.CategoryModel

class ExpenseCategoryAdapter: RecyclerView.Adapter<ExpenseCategoryAdapter.CategoryHolder>() {
    inner class CategoryHolder(var binding: ExpenseCategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }
    }
    val list = AsyncListDiffer(this, diffUtil)

    var expenseCategoryList: List<CategoryModel>
    get() = list.currentList
    set(value) = list.submitList(value)

    private var onItemClickListener: ((CategoryModel) -> Unit)? = null

    fun setOnItemClickListener(listener: ((CategoryModel) -> Unit)) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding = ExpenseCategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val expenseList = expenseCategoryList[position]
        holder.binding.sheetExpense = expenseList
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(expenseList)
            }
        }
    }

    override fun getItemCount(): Int {
        return expenseCategoryList.size
    }

}