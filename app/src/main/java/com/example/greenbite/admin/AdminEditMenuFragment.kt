package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.databinding.FragmentAdminEditMenuBinding

class AdminEditMenuFragment : Fragment() {
    lateinit var binding: FragmentAdminEditMenuBinding
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_edit_menu, container, false)

        val productID = productViewModel.activeProduct.value?.productID
        val name = productViewModel.activeProduct.value?.name
        val price = productViewModel.activeProduct.value?.price
        val rating = productViewModel.activeProduct.value?.rating

        binding.txtNameEditMenuFrag.setText(name)
        binding.txtDescEditMenuFrag.setText(price.toString())
        binding.txtDescEditMenuFrag2.setText(rating.toString())

        binding.btnConfirmEditMenuFrag.setOnClickListener(){
            val name = binding.txtNameEditMenuFrag.text.toString()
            val desc = binding.txtDescEditMenuFrag.text.toString()
            val price = binding.txtDescEditMenuFrag2.text.toString()
            productViewModel.updateProduct(productID!!, name, desc, price.toInt())
            findNavController().navigate(R.id.action_global_adminMenuFragment2)
        }
        binding.btnDeleteEditMenuFrag.setOnClickListener(){
            productViewModel.deleteProduct(productID!!)
            findNavController().navigate(R.id.action_global_adminMenuFragment2)
        }
        return inflater.inflate(R.layout.fragment_admin_edit_menu, container, false)
    }
}