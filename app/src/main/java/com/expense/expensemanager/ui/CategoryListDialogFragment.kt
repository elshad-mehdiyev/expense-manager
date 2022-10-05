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
            adapter.setOnItemClickListener { model ->
                val bundle = Bundle()
                bundle.putString("expenseAmount",expenseAmount)
                model.imagePath?.let { it1 -> bundle.putInt("path", it1) }
                model.categoryName?.let { it1 ->  bundle.putString("categoryName", it1)}
                findNavController().navigate(R.id.createExpense, bundle)
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
            incomeCategoryAdapter.setOnItemClickListener { model ->
                val bundle = Bundle()
                bundle.putString("incomeAmount",incomeAmount)
                model.imagePath?.let { it1 -> bundle.putInt("path", it1) }
                model.incomeCategoryName?.let { it1 ->  bundle.putString("IncomeCategoryName", it1)}
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
            CategoryModel(categoryName = getString(R.string.doctor), imagePath = R.drawable.category_doctor),
            CategoryModel(categoryName = getString(R.string.education), imagePath = R.drawable.category_education),
            CategoryModel(categoryName = getString(R.string.entertainment), imagePath = R.drawable.category_entertainment),
            CategoryModel(categoryName = getString(R.string.family), imagePath = R.drawable.category_family),
            CategoryModel(categoryName = getString(R.string.gift), imagePath = R.drawable.category_gift),
            CategoryModel(categoryName = getString(R.string.public_transport), imagePath = R.drawable.category_public_transport),
            CategoryModel(categoryName = getString(R.string.restaurant), imagePath = R.drawable.category_restaurant),
            CategoryModel(categoryName = getString(R.string.communal), imagePath = R.drawable.category_communal),
            CategoryModel(categoryName = getString(R.string.sport), imagePath = R.drawable.category_workout),
            CategoryModel(categoryName = getString(R.string.other), imagePath = R.drawable.category_other)
        )
    }
    private fun listOfCategoryIncome(): List<CategoryModel> {
        return listOf(
            CategoryModel(incomeCategoryName = getString(R.string.repayable_debt), imagePath = R.drawable.category_debt),
            CategoryModel(incomeCategoryName = getString(R.string.own_business), imagePath = R.drawable.category_own_business),
            CategoryModel(incomeCategoryName = getString(R.string.salary), imagePath = R.drawable.category_salary),
            CategoryModel(incomeCategoryName = getString(R.string.gift), imagePath = R.drawable.category_gift)
        )
    }

}