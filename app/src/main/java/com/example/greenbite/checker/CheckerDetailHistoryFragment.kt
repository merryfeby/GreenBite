package com.example.greenbite.checker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.R
import com.example.greenbite.databinding.FragmentCheckerDetailHistoryBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckerDetailHistoryFragment : Fragment() {
    private val checkerViewModel: CheckerViewModel by activityViewModels()
    lateinit var binding: FragmentCheckerDetailHistoryBinding
    lateinit var adapter: OrderDetailAdapter
    val format = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    val now = Date()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checker_detail_history, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkerViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeDetailHistoryChecker.text = "Welcome, ${it.name}"
            }
        }
        checkerViewModel.activeOrder.observe(viewLifecycleOwner) { order ->
            order?.let {
                binding.txtStatusDetailHistoryChecker.text = "Order is " + order.status
                binding.txtCustDetailHistoryChecker.text = order.customer_name
                binding.txtIdDetailHistoryChecker.text = "#" + order.orderID
                binding.txtPlacedDateDetailHistoryChecker.text = order.created_at
                binding.txtNameDetailHistoryChecker.text = order.customer_name
                binding.txtPhoneDetailHistoryChecker.text = order.customer_phone
                binding.txtSubtotalDetailHistoryChecker.text = "Rp."+ NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.subtotal)
                binding.txtGrandtotalDetailHistoryChecker.text = "Rp."+ NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.total)
                val inputDate: Date? = format.parse(order.created_at)
                val diffMillis = now.time - inputDate!!.time
                binding.txtTimeDetailHistoryChecker.text = "Placed " + (diffMillis / (60 * 1000)).toString() + " min ago"
            }
        }
        adapter = OrderDetailAdapter()
        binding.rvDetailHistoryChecker.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDetailHistoryChecker.adapter = adapter
        checkerViewModel.activeOrder.observe(viewLifecycleOwner){ orders ->
            if (orders != null){
                adapter.submitList(orders.order_details)
            }
        }
    }
}