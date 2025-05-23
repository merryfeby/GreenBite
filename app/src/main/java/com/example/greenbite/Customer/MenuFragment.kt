package com.example.greenbite.Customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenbite.CartViewModel
import com.example.greenbite.MenuAdapter
import com.example.greenbite.MenuViewModel
import com.example.greenbite.Product
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var menuAdapter: MenuAdapter
    private val menuViewModel: MenuViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel
        binding.menuViewModel = menuViewModel
        binding.productViewModel = productViewModel

        categoryButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMenuItems()
    }

    private fun setupRecyclerView() {
        val userEmail = userViewModel.activeUser.value?.email ?: "guest"
        menuAdapter = MenuAdapter(cartViewModel, userEmail)
        binding.rvAllMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAllMenu.adapter = menuAdapter
    }

//    private fun observeMenuItems() {
//        menuViewModel.currentCategoryMenu.observe(viewLifecycleOwner) { filteredItems ->
//            menuAdapter.submitList(filteredItems.toMutableList())
//        }
//    }
    private fun observeMenuItems() {
        productViewModel.currentCategoryMenu.observe(viewLifecycleOwner) { filteredItems: List<Product>? ->
            Log.d("MenuFragment", "Filtered items: $filteredItems")
            menuAdapter.submitList(filteredItems?.toList() ?: emptyList())
        }
    }

    private fun categoryButton() {
        val buttons = listOf(
            binding.btnAll,
            binding.btnFoods,
            binding.btnBeverages,
            binding.btnSnacks,
        )

        fun setActive(btn:TextView) {
            buttons.forEach { button ->
                if (button == btn) {
                    button.setBackgroundResource(R.drawable.button_active)
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    button.setBackgroundResource(R.drawable.button_inactive)
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
            }
        }

        binding.btnAll.setOnClickListener {
            productViewModel.setCategory("All")
            setActive(binding.btnAll)
        }
        binding.btnFoods.setOnClickListener {
            productViewModel.setCategory("Foods")
            setActive(binding.btnFoods)
        }
        binding.btnBeverages.setOnClickListener {
            productViewModel.setCategory("Beverages")
            setActive(binding.btnBeverages)
        }
        binding.btnSnacks.setOnClickListener {
            productViewModel.setCategory("Snacks")
            setActive(binding.btnSnacks)
        }

        setActive(binding.btnAll)
    }

}