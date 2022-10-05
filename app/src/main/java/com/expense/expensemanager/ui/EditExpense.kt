package com.expense.expensemanager.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.expense.expensemanager.databinding.FragmentEditExpenseBinding
import com.expense.expensemanager.viewmodel.EditTextViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditExpense : Fragment() {
    private var _binding: FragmentEditExpenseBinding? = null
    private val binding get() = _binding!!
    private var calendar = Calendar.getInstance()
    private val args: EditExpenseArgs by navArgs()
    private val viewModel: EditTextViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backFromExpenseEdit.setOnClickListener {
            val action = EditExpenseDirections.actionEditExpenseToExpensesPage()
            findNavController().navigate(action)
        }
        datePicker()
        binding.expenseAmountEdit.setText((-args.selectedExpense.expense!!).toString())
        binding.datePickerEdit.text = args.selectedExpense.date.toString()
        binding.expenseAssignEdit.setText(args.selectedExpense.assign)
        binding.updateExpense.setOnClickListener {
            updateExpense()
        }
    }
    private fun updateExpense() {
        args.selectedExpense.id?.let {
            viewModel.updateToNewExpense(expense = -binding.expenseAmountEdit.text.toString().toDouble(),
                assign = binding.expenseAssignEdit.text.toString(),
                date = binding.datePickerEdit.text.toString(),
                id = it,
                month = binding.datePickerEdit.text.toString().split("/").toTypedArray()[0],
                income = null
            )
        }
        val action = EditExpenseDirections.actionEditExpenseToExpensesPage()
        findNavController().navigate(action)
    }
    private fun datePicker() {
        binding.datePickerEdit.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    private val dateListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.datePickerEdit.text = sdf.format(calendar.time)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}