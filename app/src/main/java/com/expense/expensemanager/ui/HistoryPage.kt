package com.expense.expensemanager.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expense.expensemanager.R
import com.expense.expensemanager.adapter.ExpenseByCategoryAdapter
import com.expense.expensemanager.databinding.FragmentHistoryPageBinding
import com.expense.expensemanager.viewmodel.HistoryPageViewModel
import com.github.mikephil.charting.data.*
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
        viewModel.showExpenseByCategoryMonthly(selectedMonth)
        observe()
        createPie()
        binding.expenseMonthHistory.text = getString(list[default])
        buttonClicker()
        binding.recyclerHistory.layoutManager = LinearLayoutManager(context)
        binding.recyclerHistory.adapter = expenseByCategoryAdapter
        binding.historyBack.setOnClickListener {
            val action = HistoryPageDirections.actionHistoryPageToMainScreen()
            findNavController().navigate(action)
        }
    }
    private fun observe() {
        viewModel.expenseByCategoryMonthly.observe(viewLifecycleOwner) {
            it?.let {
                expenseByCategoryAdapter.expenseByCategoryList = it
            }
        }
    }
    private fun createPie() {

        //populatePieChart(listOfSum,listOfCategory)
    }
    private fun populatePieChart(value: List<Float>, label: List<String>) {
        val list: ArrayList<PieEntry> = ArrayList()
        for (i in value.indices) {
            val values = value[i]
            val labels = label[i]
            list.add(PieEntry(values, labels))
        }
        list.add(PieEntry(6.5f, "da"))
        val dataSet = PieDataSet(list, "selection")
        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(requireContext(),R.color.text_color))
        colors.add(ContextCompat.getColor(requireContext(),R.color.check))
        colors.add(ContextCompat.getColor(requireContext(),R.color.decorative_background_2))
        colors.add(ContextCompat.getColor(requireContext(),R.color.sea))
        dataSet.colors = colors
        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.animateY(1000)
        binding.pieChart.highlightValues(null)
        binding.pieChart.invalidate()
    }
    private fun buttonClicker() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}