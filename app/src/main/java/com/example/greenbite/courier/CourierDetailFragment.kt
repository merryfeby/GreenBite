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
import com.example.greenbite.checker.OrderDetailAdapter
import com.example.greenbite.databinding.FragmentCourierDetailBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CourierDetailFragment : Fragment() {
    private val courierViewModel: CourierViewModel by activityViewModels()
    lateinit var binding: FragmentCourierDetailBinding
    lateinit var adapter: OrderDetailAdapter
    val format = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.ENGLISH).apply {
        timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courier_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        courierViewModel.activeOrder.observe(viewLifecycleOwner) { order ->
            order?.let {
                val now = Date()
                binding.txtCustDetailCourier.text = order.customer_name
                binding.txtIdDetailCourier.text = "#" + order.orderID
                binding.txtPlacedDateDetailCourier.text = order.created_at
                binding.txtNameDetailHistoryCourier.text = order.customer_name
                binding.txtPhoneDetailCourier.text = order.customer_phone
                binding.txtSubtotalDetailCourier.text = "Rp."+ NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.subtotal)
                binding.txtGrandtotalDetailCourier.text = "Rp."+ NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.total)
                binding.txtAdressDetailCourier.text = order.address
                val inputDate: Date? = format.parse(order.created_at)
                if (inputDate != null) {
                    val diffMillis = now.time - inputDate.time
                    val diffMinutes = diffMillis / (60 * 1000)
                    binding.txtTimeDetailCourier.text = "Placed $diffMinutes min ago"
                } else {
                    binding.txtTimeDetailCourier.text = "Placed unknown time"
                }
            }
        }
        adapter = OrderDetailAdapter()
        binding.rvDetailCourier.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDetailCourier.adapter = adapter
        courierViewModel.activeOrder.observe(viewLifecycleOwner){ orders ->
            if (orders != null){
                adapter.submitList(orders.order_details)
            }
        }
        binding.btnFinishDetailCourier.setOnClickListener{
            courierViewModel.finishOrder()
            findNavController().navigate(R.id.action_global_courierDashboardFragment)
        }
    }
}