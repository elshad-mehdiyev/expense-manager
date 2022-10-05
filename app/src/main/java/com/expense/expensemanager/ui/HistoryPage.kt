package com.expense.expensemanager.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expense.expensemanager.R
import com.expense.expensemanager.adapter.ExpenseByCategoryAdapter
import com.expense.expensemanager.databinding.FragmentHistoryPageBinding
import com.expense.expensemanager.viewmodel.HistoryPageViewModel
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HistoryPage : Fragment() {
    private var _binding: FragmentHistoryPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryPageViewModel by viewModels()
    private var expenseByCategoryAdapter = ExpenseByCategoryAdapter()
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
        _binding = FragmentHistoryPageBinding.inflate(inflater, container, false)
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.history_background)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        //buttonClicker()
        binding.recyclerHistory.layoutManager = LinearLayoutManager(context)
        binding.recyclerHistory.adapter = expenseByCategoryAdapter
        binding.historyBack.setOnClickListener {
            val action = HistoryPageDirections.actionHistoryPageToMainScreen()
            findNavController().navigate(action)
        }
    }
    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.showAllData.observe(viewLifecycleOwner) {
                it?.let {
                    val categoryArray = Array(it.size) { "" }
                    val expenseArray = Array(it.size) { 0f }
                    var index = 0
                    for (a in it) {
                        if (a.expenseByCategory != null && a.category != null) {
                            categoryArray[index] = a.category.toString()
                            expenseArray[index] = a.expenseByCategory.toFloat()
                            index++
                        }
                    }
                    val map = categoryArray.reversed().zip(expenseArray.reversed()).toMap()

                    val expenseList = mutableListOf<Float>()
                    for (value in map.values) {
                        expenseList.add(value)
                    }
                    val categoryList = mutableListOf<String>()
                    for (key in map.keys) {
                        categoryList.add(key)
                    }
                    populateBarChart(expenseList, categoryList)
                }
            }
        }
    }

    private fun populateBarChart(value: List<Float>, label: List<String>) {
        val list: ArrayList<PieEntry> = ArrayList()
        for (i in value.indices) {
            val values = -value[i]
            val labels = label[i]
            list.add(PieEntry(values, labels))
        }
        val dataSet = PieDataSet(list, "")
        dataSet.valueTextSize = 15f
        dataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.centerText = "Expenses"
        binding.pieChart.setCenterTextSize(18f)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.animateY(1000)
        binding.pieChart.invalidate()
    }
  /* private fun buttonClicker() {
        binding.previousMonthExpense.setOnClickListener {
            binding.expenseMonthHistory.text = getString(list[default])
            selectedMonth = if (default < 10) "0" + (default + 2) else (default+2).toString()
            viewModel.showExpenseByCategoryMonthly(selectedMonth)
            observe()
            default--
            if (default < 0) {
                default = 0
            }
        }
        binding.nextMonthExpense.setOnClickListener {
            if (default< Calendar.getInstance().get(Calendar.MONTH)) {
                default++
                binding.expenseMonthHistory.text = getString(list[default])
                selectedMonth = if (default < 10) "0" + (default + 2) else (default+2).toString()
                viewModel.showExpenseByCategoryMonthly(selectedMonth)
                observe()
                binding.expenseMonthHistory.text = getString(list[default])
            }
        }
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}