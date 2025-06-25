package com.example.greenbite.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
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

        val buttons = listOf(
            binding.btnAllFilterReport,
            binding.btnCookingFilterReport,
            binding.btnPendingFilterReport,
            binding.btnShippedFilterReport,
            binding.btnCompletedFilterReport,
            binding.btnRejectedFilterReport,
        )

        fun setActive(btn: Button) {
            buttons.forEach { button ->
                if (button == btn) {
//                    button.setBackgroundResource(R.drawable.button_active)
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green2))

                } else {
//                    button.setBackgroundResource(R.drawable.button_inactive)
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                    button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

                }
            }
        }

        setActive(binding.btnAllFilterReport)

        binding.btnAllFilterReport.setOnClickListener {
            setActive(binding.btnAllFilterReport)
            reportAdapter.submitList(vm.orders.value)
        }
        binding.btnCompletedFilterReport.setOnClickListener {
            setActive(binding.btnCookingFilterReport)
            reportAdapter.submitList(vm.orders.value?.filter { it.status == "completed" })
        }
        binding.btnCookingFilterReport.setOnClickListener {
            setActive(binding.btnCookingFilterReport)
            reportAdapter.submitList(vm.orders.value?.filter { it.status == "cooking" })
        }
        binding.btnPendingFilterReport.setOnClickListener {
            setActive(binding.btnPendingFilterReport)
            reportAdapter.submitList(vm.orders.value?.filter { it.status == "pending" })
        }
        binding.btnShippedFilterReport.setOnClickListener {
            setActive(binding.btnShippedFilterReport)
            reportAdapter.submitList(vm.orders.value?.filter { it.status == "shipped" })
        }
        binding.btnRejectedFilterReport.setOnClickListener {
            setActive(binding.btnRejectedFilterReport)
            reportAdapter.submitList(vm.orders.value?.filter { it.status == "rejected" })
        }

        return binding.root
    }
}