package com.example.greenbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {
    lateinit var binding: FragmentAdminBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false)

        binding.btnEmployeeAdminFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminFragment_to_adminEmployeeFragment)
        }
        binding.btnProductAdminFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminFragment_to_adminMenuFragment)
        }
        binding.btnReportAdminFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminFragment_to_adminReportFragment)
        }

        return binding.root
    }
}