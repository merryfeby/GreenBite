package com.example.greenbite.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.R
import com.example.greenbite.databinding.FragmentAdminReportBinding
import kotlinx.coroutines.launch
import kotlin.collections.filter

class AdminReportFragment : Fragment() {
    lateinit var binding: FragmentAdminReportBinding
    lateinit var reportAdapter: ReportAdapter
    private val vm: ReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_report, container, false)

        lifecycleScope.launch {
            vm.getOrders()
        }
        reportAdapter = ReportAdapter()
        binding.rvReportAdminFrag.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReportAdminFrag.adapter = reportAdapter

        vm.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null) {
                reportAdapter.submitList(orders)
                Log.d("Orders", orders.toString())
            }
        }

        return binding.root
    }
}