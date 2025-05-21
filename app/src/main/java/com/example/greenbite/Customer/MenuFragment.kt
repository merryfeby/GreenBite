package com.example.greenbite.Customer

import android.os.Bundle
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
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var menuAdapter: MenuAdapter
    private val menuViewModel: MenuViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel
        binding.menuViewModel = menuViewModel

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

    private fun observeMenuItems() {
        menuViewModel.currentCategoryMenu.observe(viewLifecycleOwner) { filteredItems ->
            menuAdapter.submitList(filteredItems.toMutableList())
        }
    }

    private fun categoryButton() {
        val buttons = listOf(
            binding.btnAll,
            binding.btnSmoothie,
            binding.btnSalad,
            binding.btnLeanBowl,
            binding.btnWraps
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
            menuViewModel.setCategory("All")
            setActive(binding.btnAll)
        }
        binding.btnSmoothie.setOnClickListener {
            menuViewModel.setCategory("Smoothie")
            setActive(binding.btnSmoothie)
        }
        binding.btnSalad.setOnClickListener {
            menuViewModel.setCategory("Salad")
            setActive(binding.btnSalad)
        }
        binding.btnLeanBowl.setOnClickListener {
            menuViewModel.setCategory("Lean-Bowl")
            setActive(binding.btnLeanBowl)
        }
        binding.btnWraps.setOnClickListener {
            menuViewModel.setCategory("Wraps")
            setActive(binding.btnWraps)
        }

        setActive(binding.btnAll)
    }

}