package com.example.greenbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.databinding.FragmentAdminMenuBinding

class AdminMenuFragment : Fragment() {
    lateinit var binding: FragmentAdminMenuBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_menu, container, false)
        binding.btnAddMenuFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminMenuFragment_to_adminAddMenuFragment)
        }
        return binding.root
    }
}