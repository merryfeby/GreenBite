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
import com.example.greenbite.databinding.FragmentCourierDashboardBinding

class CourierDashboardFragment : Fragment() {
    private val courierViewModel: CourierViewModel by activityViewModels()
    lateinit var binding: FragmentCourierDashboardBinding
    lateinit var dashboardAdapter: DashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courier_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courierViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeDashboardCourier.text = "Welcome, ${it.name}"
            }
        }
        dashboardAdapter = DashboardAdapter("Deliver")
        dashboardAdapter.onDetailClickListener = { it ->
            courierViewModel.shipOrder(it)
        }
        binding.rvDashboardCourier.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDashboardCourier.adapter = dashboardAdapter
        courierViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null) {
                dashboardAdapter.submitList(orders)
            }
        }
        binding.btnAscDashboardCourier.setOnClickListener{
            courierViewModel.sortOrderAsc()
        }
        binding.btnDescDashboardCourier.setOnClickListener{
            courierViewModel.sortOrderDesc()
        }
    }

    override fun onResume() {
        super.onResume()
        courierViewModel.getCookingOrders()
    }
}