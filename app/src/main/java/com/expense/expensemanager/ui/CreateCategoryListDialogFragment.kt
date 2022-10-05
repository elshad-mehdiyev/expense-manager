package com.expense.expensemanager.ui

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.expense.expensemanager.adapter.CategoryAdapter
import com.expense.expensemanager.databinding.FragmentCreateCategoryListDialogBinding
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.viewmodel.CreateCategoryListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateCategoryListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateCategoryListDialogBinding? = null
    private val viewModel: CreateCategoryListViewModel by viewModels()
    private val adapter = CategoryAdapter()
    private val args: CreateCategoryListDialogFragmentArgs by navArgs()
    private var selectId = 0
    private var imagePath: Int? = null
    private var categoryName: String? = null
    private var categoryModel: CategoryModel? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCategoryListDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectId = args.selectId
        binding.recyclerView.layoutManager = GridLayoutManager(context,3)
        observe()
        setAdjustResize()
        adapter.setOnItemClickListener {
            imagePath = it.categoryImagePath
        }
        binding.createCategoryButton.setOnClickListener {
            createNewCategory()
            dismiss()
        }
    }

    private fun observe() {
        if (selectId == 1) {
            binding.recyclerView.adapter = adapter
            adapter.categoryList = viewModel.createCategoryImageForExpense
        }
        if (selectId == 2) {
            binding.recyclerView.adapter = adapter
            adapter.categoryList = viewModel.createCategoryImageForIncome
        }
    }
    private fun setAdjustResize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireDialog().window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        } else {
            @Suppress("DEPRECATION")
            requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
    private fun createNewCategory() {
        categoryName = binding.categoryName.text.toString()
        if (selectId == 1) {
            categoryModel = CategoryModel(categoryName = categoryName, imagePath = imagePath)
            viewModel.insertDataToCategoryModel(categoryModel!!)
        } else if(selectId == 2) {
            categoryModel = CategoryModel(incomeCategoryName = categoryName, imagePath = imagePath)
            viewModel.insertDataToCategoryModel(categoryModel!!)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}