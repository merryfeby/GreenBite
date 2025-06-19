package com.example.greenbite.Customer

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
import com.example.greenbite.OrderViewModel
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentHistoryBinding
import com.example.greenbite.HistoryAdapter
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val usersViewModel: UsersViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    private var historyAdapter = HistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        lifecycleScope.launch {
            orderViewModel.getAllOrders()
        }

        historyAdapter = HistoryAdapter()
        binding.rvHIstory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHIstory.adapter = historyAdapter

        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                userOrder(user.userID!!)
            }
        }

        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders != null) {
                val currentUser = usersViewModel.activeUser.value
                if (currentUser != null) {
                    userOrder(currentUser.userID!!)
                }
            }
        }

        return binding.root
    }

    private fun userOrder(userId: Int) {
        val allOrders = orderViewModel.orders.value
        if (allOrders != null) {
            val filteredOrders = allOrders.filter { order ->
                order.userID == userId && order.status.equals("completed", ignoreCase = true)
            }
            historyAdapter.submitList(filteredOrders)
            Log.d("FilteredOrders", "Showing ${filteredOrders.size} completed orders for user $userId")
        }
    }
}
