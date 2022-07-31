package com.expense.expensemanager.ui

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expense.expensemanager.R
import com.expense.expensemanager.adapter.ExpenseCategoryAdapter
import com.expense.expensemanager.adapter.IncomeCategoryAdapter
import com.expense.expensemanager.databinding.FragmentItemListDialogListDialogBinding
import com.expense.expensemanager.model.CategoryModel
import com.expense.expensemanager.viewmodel.CategoryListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListDialogFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentItemListDialogListDialogBinding? = null
    private val adapter = ExpenseCategoryAdapter()
    private val incomeCategoryAdapter = IncomeCategoryAdapter()
    private val viewModel: CategoryListViewModel by viewModels()
    private var selector = 0
    private var expenseAmount = ""
    private var incomeAmount = ""

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selector = arguments?.getInt("idSelector",0)!!
        expenseAmount = arguments?.getString("amount", "")!!
        incomeAmount = arguments?.getString("amount_income","")!!
        binding.createCategory.setOnClickListener {
            val action = CategoryListDialogFragmentDirections
                .actionCategoryListDialogFragmentToCreateCategoryListDialogFragment(selector)
            findNavController().navigate(action)
        }
        binding.recyclerSheet.layoutManager = LinearLayoutManager(context)
        observe()
    }
    private fun observe() {
        if (selector == 1) {
            viewModel.categoryModelData.observe(viewLifecycleOwner) {
                it?.let {
                    if (it.isEmpty()) {
                        viewModel.insertListToCategory(listOfCategory())
                    }
                    binding.recyclerSheet.adapter = adapter
                    adapter.expenseCategoryList = it.reversed()
                }
            }
            adapter.setOnItemClickListener {
                val bundle = Bundle()
                bundle.putString("expenseAmount",expenseAmount)
                it.imagePath?.let { it1 -> bundle.putInt("path", it1) }
                it.categoryName?.let { it1 ->  bundle.putString("categoryName", it1)}
                findNavController().navigate(R.id.createExpense,bundle)
                dismiss()
            }
        } else if (selector == 2) {
            viewModel.categoryIncomeModel.observe(viewLifecycleOwner) {
                it?.let {
                    if (it.isEmpty()) {
                        viewModel.insertListToCategory(listOfCategoryIncome())
                    }
                    binding.recyclerSheet.adapter = incomeCategoryAdapter
                    incomeCategoryAdapter.incomeCategoryList = it.reversed()
                }
            }
            incomeCategoryAdapter.setOnItemClickListener {
                val bundle = Bundle()
                bundle.putString("incomeAmount",incomeAmount)
                it.imagePath?.let { it1 -> bundle.putInt("path", it1) }
                it.incomeCategoryName?.let { it1 ->  bundle.putString("IncomeCategoryName", it1)}
                findNavController().navigate(R.id.createIncome,bundle)
                dismiss()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listOfCategory() : List<CategoryModel> {
        return listOf(
            CategoryModel(categoryName = getString(R.string.add), imagePath = R.drawable.category_add)
        )
    }
    private fun listOfCategoryIncome(): List<CategoryModel> {
        return listOf(
            CategoryModel(incomeCategoryName = getString(R.string.add), imagePath = R.drawable.category_add)
        )
    }

}