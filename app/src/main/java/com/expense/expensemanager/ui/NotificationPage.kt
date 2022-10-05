package com.expense.expensemanager.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expense.expensemanager.R
import com.expense.expensemanager.adapter.NotificationAdapter
import com.expense.expensemanager.databinding.FragmentNotificationPageBinding
import com.expense.expensemanager.viewmodel.NotificationPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NotificationPage : Fragment() {
    private var _binding: FragmentNotificationPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationPageViewModel by viewModels()
    private val adapter = NotificationAdapter()

    private var default = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedMonth: String = ""
        get() {
            return if (default < 10) "0" + (default + 1) else (default+1).toString()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationPageBinding.inflate(inflater,container,false)
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(),
            R.color.notification_background
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showExpenseDataMonthly(selectedMonth)
        observe()
        binding.notificationBack.setOnClickListener {
            val action = NotificationPageDirections.actionNotificationPageToMainScreen()
            findNavController().navigate(action)
        }
    }
    private fun observe() {
        viewModel.expenseMonthly.observe(viewLifecycleOwner) {
            it?.let {
                binding.recyclerNotification.layoutManager = LinearLayoutManager(context)
                binding.recyclerNotification.adapter = adapter
                adapter.notificationList = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}