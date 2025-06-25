package com.example.greenbite.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.Product
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.databinding.FragmentAdminMenuBinding
import kotlinx.coroutines.launch
import kotlin.collections.filter

class AdminMenuFragment : Fragment() {
    lateinit var binding: FragmentAdminMenuBinding
    lateinit var productAdapter: ProductAdapter
    private val viewModel: ProductViewModel by activityViewModels()
    private val vm: AdminProductViewModel by activityViewModels()

    override fun onCreateView(
        // 1. Foods 2. Beverages 3. Snack
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_menu, container, false)

        productAdapter = ProductAdapter(viewModel)
        val spanCount = 2
        binding.rvMenuAdminFrag.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.rvMenuAdminFrag.adapter = productAdapter

        viewModel.currentCategoryMenu.observe(viewLifecycleOwner) { filteredItems: List<Product>? ->
            Log.d("MenuFragment", "Filtered items: $filteredItems")
            productAdapter.submitList(filteredItems?.toList() ?: emptyList())
        }

        //refresh
        binding.button2.setOnClickListener(){
//            viewModel.setCategory("Foods")
            viewModel.AllProducts()
        }

        binding.btnAddMenuFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_global_adminAddMenuFragment2)
        }
        return binding.root
    }
}