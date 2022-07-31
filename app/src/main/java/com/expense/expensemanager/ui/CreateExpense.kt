package com.expense.expensemanager.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.expense.expensemanager.R
import com.expense.expensemanager.constant.Status
import com.expense.expensemanager.databinding.FragmentCreateExpenseBinding
import com.expense.expensemanager.viewmodel.CreateExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateExpense : Fragment() {
    private var _binding: FragmentCreateExpenseBinding? = null
    private var calendar = Calendar.getInstance()
    private val viewModel: CreateExpenseViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateExpenseBinding.inflate(inflater, container, false)
        updateDateInView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val path = arguments?.getInt("path",0)
        val categoryName = arguments?.getString("categoryName","")!!
        val expenseAmount = arguments?.getString("expenseAmount", "")
        if (expenseAmount != null) {
            binding.expenseAmount.setText(expenseAmount)
        }
        observe()
        binding.backFromExpense.setOnClickListener {
            val action = CreateExpenseDirections.actionCreateExpenseToMainScreen()
            findNavController().navigate(action)
        }
        binding.categoryExpense.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("amount",binding.expenseAmount.text.toString())
            bundle.putInt("idSelector",1)
            it.findNavController().navigate(R.id.categoryListDialogFragment,bundle)
        }
        binding.datePicker.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
            binding.readyExpense.setOnClickListener {
                viewModel.saveExpense(
                    amount = binding.expenseAmount.text.toString(),
                    assign = binding.expenseAssign.text.toString(),
                    date = binding.datePicker.text.toString(),
                    iconPath = path,
                    category = categoryName)
                    viewModel.updateExpense(categoryName,binding.expenseAmount.text.toString())
            }
    }

    private fun observe() {
        viewModel.insertMessage.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show()
                    val action = CreateExpenseDirections.actionCreateExpenseToMainScreen()
                    findNavController().navigate(action)
                    viewModel.resetInsertMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.datePicker.text = sdf.format(calendar.time)
    }
}