package com.example.greenbite.checker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.MenuAdapter
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentCheckerDashboardBinding

class CheckerDashboardFragment : Fragment() {
    private val checkerViewModel: CheckerViewModel by activityViewModels()
    lateinit var binding: FragmentCheckerDashboardBinding
    lateinit var dashboardAdapter: DashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checker_dashboard, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkerViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeDashboardChecker.text = "Welcome, ${it.name}"
            }
        }
        dashboardAdapter = DashboardAdapter()
        dashboardAdapter.onDetailClickListener = { it ->
            checkerViewModel.setActiveOrder(it)
            findNavController().navigate(R.id.action_global_checkerDetailFragment)
        }
        binding.rvDashboardChecker.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDashboardChecker.adapter = dashboardAdapter
        checkerViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null) {
                dashboardAdapter.submitList(orders)
            }
        }
        binding.btnAscDashboardChecker.setOnClickListener{
            checkerViewModel.sortOrderAsc()
        }
        binding.btnDescDashboardChecker.setOnClickListener{
            checkerViewModel.sortOrderDesc()
        }
    }
}