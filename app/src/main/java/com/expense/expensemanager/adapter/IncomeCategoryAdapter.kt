package com.expense.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.databinding.FragmentItemListDialogListDialogItemBinding
import com.expense.expensemanager.model.CategoryModel

class IncomeCategoryAdapter: RecyclerView.Adapter<IncomeCategoryAdapter.IncomeCategoryHolder>() {
    inner class IncomeCategoryHolder(var binding: FragmentItemListDialogListDialogItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }
    }
    private val list = AsyncListDiffer(this,diffUtil)

    var incomeCategoryList: List<CategoryModel>
    get() = list.currentList
    set(value) = list.submitList(value)

    private var onItemClickListener: ((CategoryModel) -> Unit)? = null

    fun setOnItemClickListener(listener: ((CategoryModel) -> Unit)) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeCategoryHolder {
        val binding = FragmentItemListDialogListDialogItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IncomeCategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: IncomeCategoryHolder, position: Int) {
        val incomeList = incomeCategoryList[position]
        holder.binding.sheet = incomeList
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(incomeList)
            }
        }
    }

    override fun getItemCount(): Int {
        return incomeCategoryList.size
    }
}