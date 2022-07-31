package com.expense.expensemanager.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expense.expensemanager.R
import com.expense.expensemanager.adapter.ExpenseAdapter
import com.expense.expensemanager.databinding.FragmentExpensesPageBinding
import com.expense.expensemanager.viewmodel.ExpensesPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ExpensesPage : Fragment() {
    private var _binding: FragmentExpensesPageBinding? = null
    private val viewModel: ExpensesPageViewModel by viewModels()
    private val binding get() = _binding!!
    private var expenseAdapter = ExpenseAdapter()
    private var default = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedMonth: String = ""
        get() {
        return if (default < 10) "0" + (default + 1) else (default+1).toString()
    }
    private val list = listOf(R.string.january,R.string.february,R.string.mart,R.string.april,
    R.string.may,R.string.june,R.string.july,R.string.august,R.string.september,R.string.october,
    R.string.november,R.string.december)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesPageBinding.inflate(inflater, container, false)
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.expense_background)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showExpenseDataMonthly(selectedMonth)
        observe()
        binding.expenseMonth.text = getString(list[default])
        buttonClicker()
        binding.recyclerExpense.layoutManager = LinearLayoutManager(context)
        binding.recyclerExpense.adapter = expenseAdapter
        binding.expenseBack.setOnClickListener {
            val action = ExpensesPageDirections.actionExpensesPageToMainScreen()
            findNavController().navigate(action)
        }
    }

    /**
     * Observer  for  expenseMonthly
     */
    private fun observe() {
        viewModel.expenseMonthly.observe(viewLifecycleOwner) {
            it?.let {
                expenseAdapter.expenseList = it.reversed()
            }
        }
    }
    /**
     * Previous  and  next button  for  month  change
     */
    private fun buttonClicker() {
        binding.previousMonthExpense.setOnClickListener {
            binding.expenseMonth.text = getString(list[default])
            selectedMonth = if (default < 10) "0" + (default + 2) else (default+2).toString()
            viewModel.showExpenseDataMonthly(selectedMonth)
            observe()
            default--
            if (default < 0) {
                default = 0
            }
        }
        binding.nextMonthExpense.setOnClickListener {
            if (default< Calendar.getInstance().get(Calendar.MONTH)) {
                default++
                binding.expenseMonth.text = getString(list[default])
                selectedMonth = if (default < 10) "0" + (default + 2) else (default+2).toString()
                viewModel.showExpenseDataMonthly(selectedMonth)
                observe()
                binding.expenseMonth.text = getString(list[default])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}