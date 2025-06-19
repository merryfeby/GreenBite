package com.example.greenbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.checker.Order
import com.example.greenbite.databinding.FragmentCustomerDetailHistoryBinding
import java.text.NumberFormat
import java.util.Locale

class CustomerDetailHistoryFragment : Fragment() {
    lateinit var binding: FragmentCustomerDetailHistoryBinding
    private val usersViewModel: UsersViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var adapter: CustomerHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_detail_history, container, false)

        binding.usersViewModel = usersViewModel
        binding.orderViewModel = orderViewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val orderID = arguments?.getInt("orderID") ?: 0

        setupRecyclerView()

        observeSelectedOrder()

        orderViewModel.selectOrder(orderID)


        binding.detailHistoryBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = CustomerHistoryAdapter()
        binding.RvHistoryDetail?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CustomerDetailHistoryFragment.adapter
            visibility = View.VISIBLE
        }
    }

    private fun observeSelectedOrder() {
        orderViewModel.selectedOrder.observe(viewLifecycleOwner) { order ->
            order?.let {
                orderDetails(it)
            }
        }
    }

    private fun orderDetails(order: Order) {
        binding.customerName?.text = order.customer_name
        binding.customerAddress?.text = usersViewModel.activeUser.value?.address
        binding.deliveryTime?.text = order.created_at

        val formattedSubtotal = NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.subtotal)
        val formattedShipping = NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.shipping_fee)
        val formattedTotal = NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.total)

        binding.subtotalAmount?.text = "Rp $formattedSubtotal"
        binding.deliveryFeeAmount?.text = if (order.shipping_fee > 0) "Rp $formattedShipping" else "Free"
        binding.totalAmount?.text = "Rp $formattedTotal"

        if (order.order_details.isNotEmpty()) {
            adapter.submitList(order.order_details)
            binding.RvHistoryDetail?.visibility = View.VISIBLE
        } else {
            binding.RvHistoryDetail?.visibility = View.GONE
        }
    }
}