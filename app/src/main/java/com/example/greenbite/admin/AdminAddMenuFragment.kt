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
import com.example.greenbite.databinding.FragmentAdminAddMenuBinding

class AdminAddMenuFragment : Fragment() {
    lateinit var binding: FragmentAdminAddMenuBinding
    private val vm: ProductViewModel by activityViewModels()
//    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_menu, container, false)

        binding.btnConfirmAddProductFrag.setOnClickListener(){
            val name = binding.txtNameAddProductFrag.text.toString()
            val category = binding.txtCategoryAddProductFrag.text.toString()
            val price = binding.txtPriceAddProductFrag.text.toString()
            val description = binding.txtDescriptionAddProductFrag.text.toString()
            val fat = binding.txtFatAddProductFrag.text.toString()
            val protein = binding.txtProteinAddProductFrag.text.toString()
            val calorie = binding.txtCalorieAddProductFrag.text.toString()

            var categoryId = 0
            if(category == "Food"){
                categoryId = 1
            }else if(category == "Drink"){
                categoryId = 2
            }else if(category == "Snack"){
                categoryId = 3
            }

            vm.addProduct(name, description, price, categoryId, fat.toInt(), protein.toInt(), calorie.toInt())
            findNavController().navigate(R.id.action_global_adminMenuFragment2)
        }
        binding.btnCancelAddProductFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_global_adminMenuFragment2)
        }

        return binding.root
    }
}