package com.example.greenbite.checker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.R
import com.example.greenbite.databinding.FragmentCheckerHistoryBinding

class CheckerHistoryFragment : Fragment() {
    private val checkerViewModel: CheckerViewModel by activityViewModels()
    lateinit var binding: FragmentCheckerHistoryBinding
    lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checker_history, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkerViewModel.getActiveOrders()
        checkerViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeHistoryChecker.text = "Welcome, ${it.name}"
            }
        }
        historyAdapter = HistoryAdapter()
        historyAdapter.onDetailClickListener = { it ->
            checkerViewModel.setActiveOrder(it)
            findNavController().navigate(R.id.action_global_checkerDetailHistoryFragment)
        }
        binding.rvHistoryChecker.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistoryChecker.adapter = historyAdapter
        checkerViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null){
                historyAdapter.submitList(orders)
            }
        }
        binding.btnAscHistoryChecker.setOnClickListener{
            checkerViewModel.sortOrderAsc()
        }
        binding.btnDescHistoryChecker.setOnClickListener{
            checkerViewModel.sortOrderDesc()
        }
    }

    override fun onResume() {
        super.onResume()
        checkerViewModel.getActiveOrders()
    }
}