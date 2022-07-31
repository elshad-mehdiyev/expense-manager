package com.expense.expensemanager.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expense.expensemanager.R
import com.expense.expensemanager.databinding.FragmentMainScreenBinding
import com.expense.expensemanager.viewmodel.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal
import java.math.RoundingMode

@AndroidEntryPoint
class MainScreen : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater,container,false)
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(),
            R.color.main_screen_background
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        binding.expenseFab.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToCreateExpense()
            findNavController().navigate(action)
        }
        binding.incomeFab.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToCreateIncome()
            findNavController().navigate(action)
        }
        binding.mainExpense.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToExpensesPage()
            findNavController().navigate(action)
        }
        binding.mainHistory.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToHistoryPage()
            findNavController().navigate(action)
        }
        binding.mainIncome.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToIncomePage()
            findNavController().navigate(action)
        }
    }
    private fun observe() {
        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
                expense?.let {
                    binding.mainscreenTotalExpense.text = "-$" + -it
                }
                income?.let {
                    binding.mainscreenTotalIncome.text = "+$$it"
                }
                expense?.let {
                    income?.let {
                        binding.mainscreeBalance.text =
                            BigDecimal(income + expense).setScale(2, RoundingMode.HALF_EVEN).toString()
                    }
                }
                if(expense == null && income != null) {
                    binding.mainscreeBalance.text =
                        BigDecimal(income).setScale(2, RoundingMode.HALF_EVEN).toString()
                }else if(expense != null && income == null) {
                    binding.mainscreeBalance.text =
                        BigDecimal(expense).setScale(2, RoundingMode.HALF_EVEN).toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}