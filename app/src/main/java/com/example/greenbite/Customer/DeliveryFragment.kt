package com.example.greenbite.Customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.DeliveryAdapter
import com.example.greenbite.OrderViewModel
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentDeliveryBinding
import kotlinx.coroutines.launch

class DeliveryFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryBinding
    private val usersViewModel: UsersViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    private var deliveryAdapter = DeliveryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery, container, false)

        lifecycleScope.launch {
            orderViewModel.getAllOrders()
        }

        binding.RvDelivery.layoutManager = LinearLayoutManager(requireContext())
        binding.RvDelivery.adapter = deliveryAdapter

        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                userDeliveryOrder(user.userID!!)
            }
        }

        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null){
                Log.d("AAA", "onCreateView: $orders")
                val currentUser = usersViewModel.activeUser.value
                if (currentUser != null) {
                    userDeliveryOrder(currentUser.userID!!)
                }
//                binding.tvDelivery.visibility = View.GONE
            }
//            if (orders != null) {
//
//            }
        }
        return binding.root
    }
    private fun userDeliveryOrder(userId: Int) {
        val allOrders = orderViewModel.orders.value
        if (allOrders != null) {
            val filteredOrders = allOrders.filter { order ->
                order.userID == userId && order.status.equals("shipping", ignoreCase = true)
            }
            if (filteredOrders.isEmpty()){
                binding.tvDelivery.visibility = View.VISIBLE
            }
            else{
                deliveryAdapter.submitList(filteredOrders)
                binding.tvDelivery.visibility = View.GONE
            }
            Log.d("FilteredOrders", "Showing ${filteredOrders.size} shipped orders for user $userId")
        }
    }

}