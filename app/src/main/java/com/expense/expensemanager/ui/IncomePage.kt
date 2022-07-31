package com.expense.expensemanager.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expense.expensemanager.R
import com.expense.expensemanager.adapter.IncomeAdapter
import com.expense.expensemanager.databinding.FragmentIncomePageBinding
import com.expense.expensemanager.viewmodel.IncomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class IncomePage : Fragment() {
    private var _binding: FragmentIncomePageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IncomePageViewModel by viewModels()
    private val adapter = IncomeAdapter()
    var default = Calendar.getInstance().get(Calendar.MONTH)
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
        _binding = FragmentIncomePageBinding.inflate(inflater, container, false)
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.income_background)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showIncomeDataMonthly(selectedMonth)
        observe()
        binding.incomeMonth.text = getString(list[default])
        buttonClicker()
        binding.recylerIncome.layoutManager = LinearLayoutManager(context)
        binding.recylerIncome.adapter = adapter
        binding.incomeBack.setOnClickListener {
            val action = IncomePageDirections.actionIncomePageToMainScreen()
            findNavController().navigate(action)
        }
    }

    private fun observe() {
        viewModel.incomeMonthly.observe(viewLifecycleOwner) {
            it?.let {
                adapter.incomeList = it.reversed()
            }
        }
    }

    private fun buttonClicker() {
        binding.previousMonthIncome.setOnClickListener {
            binding.incomeMonth.text = getString(list[default])
            selectedMonth = if (default < 10) "0" + (default + 2) else (default+2).toString()
            viewModel.showIncomeDataMonthly(selectedMonth)
            observe()
            default--
            if (default < 0) {
                default = 0
            }
        }
        binding.nextMonthIncome.setOnClickListener {
            if (default< Calendar.getInstance().get(Calendar.MONTH)) {
                default++
                binding.incomeMonth.text = getString(list[default])
                selectedMonth = if (default < 10) "0" + (default + 2) else (default+2).toString()
                viewModel.showIncomeDataMonthly(selectedMonth)
                observe()
                binding.incomeMonth.text = getString(list[default])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}