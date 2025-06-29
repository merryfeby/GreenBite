package com.example.greenbite.courier

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
import com.example.greenbite.databinding.FragmentCourierDeliveryBinding
import com.example.greenbite.databinding.FragmentCourierHistoryBinding

class CourierHistoryFragment : Fragment() {
    private val courierViewModel: CourierViewModel by activityViewModels()
    lateinit var binding: FragmentCourierHistoryBinding
    lateinit var dashboardAdapter: DashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courier_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courierViewModel.getCompletedOrders()
        courierViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeHistoryCourier.text = "Welcome, ${it.name}"
            }
        }
        dashboardAdapter = DashboardAdapter("Detail")
        dashboardAdapter.onDetailClickListener = { it ->
            courierViewModel.setActiveOrder(it)
            findNavController().navigate(R.id.action_global_courierDetailHistoryFragment)
        }
        binding.rvHistoryCourier.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistoryCourier.adapter = dashboardAdapter
        courierViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null) {
                dashboardAdapter.submitList(orders)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        courierViewModel.getCompletedOrders()
    }
}