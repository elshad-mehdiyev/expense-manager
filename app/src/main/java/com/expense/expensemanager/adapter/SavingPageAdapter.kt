package com.expense.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.databinding.CreateSavingItemBinding
import com.expense.expensemanager.model.CreateSavingGoalModel

class SavingPageAdapter: RecyclerView.Adapter<SavingPageAdapter.SaveHolder>() {
    inner class SaveHolder(var binding: CreateSavingItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<CreateSavingGoalModel>() {
        override fun areItemsTheSame(oldItem: CreateSavingGoalModel, newItem: CreateSavingGoalModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: CreateSavingGoalModel, newItem: CreateSavingGoalModel): Boolean {
            return oldItem == newItem
        }
    }

    private val list = AsyncListDiffer(this, diffUtil)
    var savingAdapterList: List<CreateSavingGoalModel>
    get() = list.currentList
    set(value) = list.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveHolder {
        val binding = CreateSavingItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SaveHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveHolder, position: Int) {
        holder.binding.goal = savingAdapterList[position]
    }

    override fun getItemCount(): Int {
        return savingAdapterList.size
    }
}