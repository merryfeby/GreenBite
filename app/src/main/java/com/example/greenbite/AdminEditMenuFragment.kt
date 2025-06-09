package com.example.greenbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.databinding.FragmentAdminEditMenuBinding

class AdminEditMenuFragment : Fragment() {
    lateinit var binding: FragmentAdminEditMenuBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_edit_menu, container, false)
        binding.btnConfirmEditMenuFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminEditMenuFragment_pop)
        }
        binding.btnDeleteEditMenuFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_adminEditMenuFragment_pop)
        }
        return inflater.inflate(R.layout.fragment_admin_edit_menu, container, false)
    }
}