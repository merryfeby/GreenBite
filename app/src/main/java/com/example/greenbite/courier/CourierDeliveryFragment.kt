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

class CourierDeliveryFragment : Fragment() {
    private val courierViewModel: CourierViewModel by activityViewModels()
    lateinit var binding: FragmentCourierDeliveryBinding
    lateinit var dashboardAdapter: DashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courier_delivery, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courierViewModel.getShippingOrders()
        courierViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeDeliveryCourier.text = "Welcome, ${it.name}"
            }
        }
        dashboardAdapter = DashboardAdapter("Details")
        dashboardAdapter.onDetailClickListener = { it ->
            courierViewModel.setActiveOrder(it)
            findNavController().navigate(R.id.action_global_courierDetailFragment)
        }
        binding.rvDeliveryCourier.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDeliveryCourier.adapter = dashboardAdapter
        courierViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null) {
                dashboardAdapter.submitList(orders)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        courierViewModel.getShippingOrders()
    }
}