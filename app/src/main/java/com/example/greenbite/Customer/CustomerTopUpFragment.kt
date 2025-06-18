package com.example.greenbite.Customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentCustomerTopUpBinding


class CustomerTopUpFragment : Fragment() {
    private lateinit var binding: FragmentCustomerTopUpBinding
    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_top_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.usersViewModel = usersViewModel

        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.tvMybalanceCredit.text = "Rp ${user?.credit ?: "0"}"
        }
    }

}