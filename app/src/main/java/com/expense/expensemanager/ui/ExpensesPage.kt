package com.expense.expensemanager.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expense.expensemanager.R
import com.expense.expensemanager.adapter.ExpenseAdapter
import com.expense.expensemanager.databinding.FragmentExpensesPageBinding
import com.expense.expensemanager.viewmodel.ExpensesPageViewModel
import com.google.android.material.snackbar.Snackbar
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
        deleteExpenseItem()
        binding.expenseMonth.text = getString(list[default])
        buttonClicker()
        binding.recyclerExpense.layoutManager = LinearLayoutManager(context)
        binding.recyclerExpense.adapter = expenseAdapter
        binding.expenseBack.setOnClickListener {
            val action = ExpensesPageDirections.actionExpensesPageToMainScreen()
            findNavController().navigate(action)
        }
        expenseAdapter.setOnClickListener {
            val action = ExpensesPageDirections.actionExpensesPageToEditExpense(it)
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

    private fun buttonClicker() {
        binding.previousMonthExpense.setOnClickListener {
            default--
            if (default < 0) {
                default = 0
            }
            binding.expenseMonth.text = getString(list[default])
            selectedMonth = if (default < 10) "0" + (default + 2) else (default+2).toString()
            viewModel.showExpenseDataMonthly(selectedMonth)
            observe()
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

    private fun deleteExpenseItem() {
        val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val expense = expenseAdapter.expenseList[position]
                viewModel.deleteExpenseItem(expense)
                Snackbar.make(requireView(),"Succesfully  delete  item",Snackbar.LENGTH_LONG).apply {
                    setAction("undo") {
                        viewModel.saveExpenseItem(expense)
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.recyclerExpense)
        }
        observe()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}