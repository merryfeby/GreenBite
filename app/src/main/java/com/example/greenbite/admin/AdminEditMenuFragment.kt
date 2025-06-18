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
    private val vm: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_edit_menu, container, false)

        val productID = vm.activeProduct.value?.productID
        val name = vm.activeProduct.value?.name
        val desc = vm.activeProduct.value?.description
        val price = vm.activeProduct.value?.price

        binding.txtNameEditMenuFrag.setText(name)
        binding.txtDescEditMenuFrag.setText(desc)
        binding.txtDescEditMenuFrag2.setText(price.toString()) //price

        binding.btnConfirmEditMenuFrag.setOnClickListener(){
            val updatedName = binding.txtNameEditMenuFrag.text.toString()
            val updatedDesc = binding.txtDescEditMenuFrag.text.toString()
            val updatedPrice = binding.txtDescEditMenuFrag2.text.toString()

            vm.updateProduct(productID!!, updatedName, updatedDesc, updatedPrice)
            findNavController().navigate(R.id.action_global_adminMenuFragment2)
//            findNavController().navigate(R.id.action_global_adminFragment3)
        }
        binding.btnDeleteEditMenuFrag.setOnClickListener(){
            vm.deleteProduct(productID!!)
            findNavController().navigate(R.id.action_global_adminMenuFragment2)
        }
        return binding.root
    }
}