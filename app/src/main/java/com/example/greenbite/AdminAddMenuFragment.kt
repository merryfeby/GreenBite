package com.example.greenbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.databinding.FragmentAdminAddMenuBinding

class AdminAddMenuFragment : Fragment() {
    lateinit var binding: FragmentAdminAddMenuBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_menu, container, false)

        binding.btnConfirmAddProductFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminAddMenuFragment_pop)
        }
        binding.btnCancelAddProductFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminAddMenuFragment_pop)
        }

        return binding.root
    }
}