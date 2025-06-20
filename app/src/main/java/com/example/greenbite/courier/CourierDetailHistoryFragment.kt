package com.example.greenbite.courier

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.R
import com.example.greenbite.checker.OrderDetailAdapter
import com.example.greenbite.databinding.FragmentCourierDetailHistoryBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CourierDetailHistoryFragment : Fragment() {
    private val courierViewModel: CourierViewModel by activityViewModels()
    lateinit var binding: FragmentCourierDetailHistoryBinding
    lateinit var adapter: OrderDetailAdapter
    val format = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    val now = Date()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courier_detail_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courierViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeDetailHistoryCourier.text = "Welcome, ${it.name}"
            }
        }
        courierViewModel.activeOrder.observe(viewLifecycleOwner) { order ->
            order?.let {
                binding.txtStatusDetailHistoryCourier.text = "Order is " + order.status
                binding.txtCustDetailHistoryCourier.text = order.customer_name
                binding.txtIdDetailHistoryCourier.text = "#" + order.orderID
                binding.txtPlacedDateDetailHistoryCourier.text = order.created_at
                binding.txtNameDetailHistoryCourier.text = order.customer_name
                binding.txtPhoneDetailHistoryCourier.text = order.customer_phone
                binding.txtSubtotalDetailHistoryCourier.text = "Rp."+ NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.subtotal)
                binding.txtGrandtotalDetailHistoryCourier.text = "Rp."+ NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.total)
                val inputDate: Date? = format.parse(order.created_at)
                val diffMillis = now.time - inputDate!!.time
                binding.txtTimeDetailHistoryCourier.text = "Placed " + (diffMillis / (60 * 1000)).toString() + " min ago"
            }
        }
        adapter = OrderDetailAdapter()
        binding.rvDetailHistoryCourier.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDetailHistoryCourier.adapter = adapter
        courierViewModel.activeOrder.observe(viewLifecycleOwner){ orders ->
            if (orders != null){
                adapter.submitList(orders.order_details)
            }
        }
    }
}