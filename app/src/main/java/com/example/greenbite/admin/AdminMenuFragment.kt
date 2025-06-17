package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.databinding.FragmentAdminMenuBinding
import kotlin.collections.filter

class AdminMenuFragment : Fragment() {
    lateinit var binding: FragmentAdminMenuBinding
    lateinit var productAdapter: ProductAdapter
    private val viewModel: ProductViewModel by activityViewModels()
    private val vm: AdminProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_menu, container, false)

        productAdapter = ProductAdapter()
        val spanCount = 2
        binding.rvMenuAdminFrag.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.rvMenuAdminFrag.adapter = productAdapter

        viewModel.currentCategoryMenu.observe(viewLifecycleOwner) { productList ->
            if (productList != null) {
                productAdapter.submitList(productList)
            }
        }

        binding.btnAddMenuFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_global_adminAddMenuFragment2)
        }
        return binding.root
    }
}