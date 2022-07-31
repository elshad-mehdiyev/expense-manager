package com.expense.expensemanager.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.expense.expensemanager.R
import com.expense.expensemanager.constant.Status
import com.expense.expensemanager.databinding.FragmentCreateIncomeBinding
import com.expense.expensemanager.viewmodel.CreateIncomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateIncome : Fragment() {
    private var _binding: FragmentCreateIncomeBinding? = null
    private var calendar = Calendar.getInstance()
    private val viewModel: CreateIncomeViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateIncomeBinding.inflate(inflater, container, false)
        updateDateInView()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val path = arguments?.getInt("path",0)
        val incomeAmount = arguments?.getString("incomeAmount", "")
        val categoryName = arguments?.getString("IncomeCategoryName","")!!
        if (incomeAmount != null) {
            binding.incomeAmount.setText(incomeAmount)
        }
        binding.categoryIncome.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("amount_income",binding.incomeAmount.text.toString())
            bundle.putInt("idSelector",2)
            it.findNavController().navigate(R.id.categoryListDialogFragment, bundle)
        }
        observe()
        binding.backFromIncome.setOnClickListener {
            val action = CreateIncomeDirections.actionCreateIncomeToMainScreen()
            findNavController().navigate(action)
        }
        binding.datePickerIncome.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.buttonIncome.setOnClickListener {
            viewModel.saveIncome(
                assign = binding.assignIncome.text.toString(),
                amount = binding.incomeAmount.text.toString(),
                date = binding.datePickerIncome.text.toString(),
                iconPath = path,
                category = categoryName
            )
        }
    }
    private fun observe() {
        viewModel.insertMessage.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show()
                    val action = CreateIncomeDirections.actionCreateIncomeToMainScreen()
                    findNavController().navigate(action)
                    viewModel.resetInsertMsg()}
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {}
            }
        }
    }
    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateInView()
    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.datePickerIncome.text = sdf.format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}