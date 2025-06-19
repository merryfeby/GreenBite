package com.example.greenbite.Customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.OrderViewModel
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentHistoryBinding
import com.example.greenbite.HistoryAdapter

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val usersViewModel: UsersViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    private val historyAdapter = HistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHIstory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            Log.d("HistoryFragment", "User is: $user")
            user?.userID?.let { userId ->

            }
        }

    }
}
