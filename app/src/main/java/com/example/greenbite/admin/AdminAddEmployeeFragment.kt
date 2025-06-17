package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.databinding.FragmentAdminAddEmployeeBinding


class AdminAddEmployeeFragment : Fragment() {
    lateinit var binding: FragmentAdminAddEmployeeBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_employee, container, false)

        binding.btnConfirmAddEmpFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminAddEmployeeFragment_pop)
        }
        binding.btnCancelAddEmpFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminAddEmployeeFragment_pop)

        }

        return binding.root
    }
}