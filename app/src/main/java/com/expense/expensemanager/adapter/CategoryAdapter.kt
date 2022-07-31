package com.expense.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.databinding.FragmentCreateCategoryListDialogItemBinding
import com.expense.expensemanager.model.CreateCategoryModel

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    inner class CategoryHolder(var binding: FragmentCreateCategoryListDialogItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<CreateCategoryModel>() {
        override fun areItemsTheSame(oldItem: CreateCategoryModel, newItem: CreateCategoryModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CreateCategoryModel, newItem: CreateCategoryModel): Boolean {
            return oldItem == newItem
        }
    }
    private val list = AsyncListDiffer(this, diffUtil)

    var categoryList: List<CreateCategoryModel>
    get() = list.currentList
    set(value) = list.submitList(value)

    private var onItemClickListener: ((CreateCategoryModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (CreateCategoryModel) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding = FragmentCreateCategoryListDialogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val createCategoryList = categoryList[position]
        holder.binding.create = createCategoryList
        holder.itemView.setOnClickListener {
            it?.let {
                onItemClickListener.let { v->
                    v?.let { it1 ->
                        it1(createCategoryList)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}