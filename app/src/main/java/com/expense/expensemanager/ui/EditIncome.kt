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
import com.expense.expensemanager.databinding.FragmentEditIncomeBinding
import com.expense.expensemanager.viewmodel.EditTextViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditIncome : Fragment() {
    private var _binding: FragmentEditIncomeBinding? = null
    private val binding get() = _binding!!
    private var calendar = Calendar.getInstance()
    private val args: EditIncomeArgs by navArgs()
    private val viewModel: EditTextViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditIncomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backFromIncomeEdit.setOnClickListener {
            val action = EditIncomeDirections.actionEditIncomeToIncomePage()
            findNavController().navigate(action)
        }
        datePicker()
        binding.incomeAmountEdit.setText(args.selectedExpense.income.toString())
        binding.datePickerEditIncome.text = args.selectedExpense.date.toString()
        binding.incomeAssignEdit.setText(args.selectedExpense.assign)
        binding.updateIncome.setOnClickListener {
            updateExpense()
        }
    }
    private fun updateExpense() {
        args.selectedExpense.id?.let {
            viewModel.updateToNewExpense(income = -binding.incomeAmountEdit.text.toString().toDouble(),
                assign = binding.incomeAssignEdit.text.toString(),
                date = binding.datePickerEditIncome.text.toString(),
                id = it,
                month = binding.datePickerEditIncome.text.toString().split("/").toTypedArray()[0],
                expense = null
            )
        }
        val action = EditIncomeDirections.actionEditIncomeToIncomePage()
        findNavController().navigate(action)
    }
    private fun datePicker() {
        binding.datePickerEditIncome.setOnClickListener {
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
        binding.datePickerEditIncome.text = sdf.format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}