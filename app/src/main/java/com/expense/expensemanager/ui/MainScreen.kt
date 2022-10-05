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
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainScreen : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainScreenViewModel by viewModels()
    private var tabSelector = 1

    private var default = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedMonth: String = ""
        get() {
            return if (default < 10) "0" + (default + 1) else (default+1).toString()
        }

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
        tabSelect()
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
        binding.notificationButton.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToNotificationPage()
            findNavController().navigate(action)
        }
        binding.mainScreenSaving.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToSavingPage()
            findNavController().navigate(action)
        }
    }
    private fun observe() {
        viewModel.showMonthlyExpense(selectedMonth)
        viewModel.showMonthlyIncome(selectedMonth)
        if(tabSelector == 2) {
            viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
                viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
                    expense?.let {
                        binding.mainscreenTotalExpense.text = "$it"
                    }
                    income?.let {
                        binding.mainscreenTotalIncome.text = "$it"
                    }
                    expense?.let {
                        income?.let {
                            binding.mainscreeBalance.text =
                                String.format("%.2f", income + expense)
                        }
                    }
                    if (expense == null && income != null) {
                        binding.mainscreeBalance.text =
                            String.format("%.2f", income)
                    } else if (expense != null && income == null) {
                        binding.mainscreeBalance.text =
                            String.format("%.2f", expense)
                    }
                }
            }
        }
        else if(tabSelector == 1) {
            viewModel.showMonthlyTotalIncome.observe(viewLifecycleOwner) { income ->
                viewModel.showMonthlyTotalExpense.observe(viewLifecycleOwner) { expense ->
                    expense?.let {
                        binding.mainscreenTotalExpense.text = "$it"
                    }
                    income?.let {
                        binding.mainscreenTotalIncome.text = "$it"
                    }
                    expense?.let {
                        income?.let {
                            binding.mainscreeBalance.text =
                                String.format("%.2f", income + expense)
                        }
                    }
                    if (expense == null && income != null) {
                        binding.mainscreeBalance.text =
                            String.format("%.2f", income)
                    } else if (expense != null && income == null) {
                        binding.mainscreeBalance.text =
                            String.format("%.2f", expense)
                    }
                }
            }
        }
    }
    private fun tabSelect() {
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tabSelector = 1
                        observe()
                    }
                    1 -> {
                        tabSelector = 2
                        observe()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}