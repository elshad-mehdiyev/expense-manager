package com.expense.expensemanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expense.expensemanager.adapter.SavingPageAdapter
import com.expense.expensemanager.databinding.FragmentSavingPageBinding
import com.expense.expensemanager.viewmodel.SavingPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SavingPage: Fragment() {
    private var _binding: FragmentSavingPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SavingPageViewModel by viewModels()
    private val adapter = SavingPageAdapter()

    private var default = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedMonth: String = ""
        get() {
            return if (default < 10) "0" + (default + 1) else (default+1).toString()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavingPageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        binding.savingBack.setOnClickListener {
            val action = SavingPageDirections.actionSavingPageToMainScreen()
            findNavController().navigate(action)
        }
        binding.newGoal.setOnClickListener {
            val action = SavingPageDirections.actionSavingPageToCreateSavingSheetFragment()
            findNavController().navigate(action)
        }
    }
    private fun observe() {
        viewModel.showMonthlyExpense(selectedMonth)
        viewModel.showMonthlyIncome(selectedMonth)
        viewModel.goalList.observe(viewLifecycleOwner) {
            binding.recyclerSave.layoutManager = LinearLayoutManager(context)
            binding.recyclerSave.adapter = adapter
            it?.let {
                adapter.savingAdapterList = it.reversed()
            }
        }
        viewModel.totalExpense.observe(viewLifecycleOwner) { expenseTotal ->
            viewModel.totalIncome.observe(viewLifecycleOwner) { incomeTotal ->
                viewModel.showMonthlyTotalExpense.observe(viewLifecycleOwner) { expenseMonth ->
                    viewModel.showMonthlyTotalIncome.observe(viewLifecycleOwner) { incomeMonth ->
                        var total = (incomeTotal + expenseTotal - (incomeMonth + expenseMonth))
                        if (total < 0.0) {
                            total = 0.0
                        }
                        binding.totalSaving.text = total.toString()
                    }
                }
            }
        }
    }
}