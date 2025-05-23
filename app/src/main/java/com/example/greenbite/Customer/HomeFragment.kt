package com.example.greenbite.Customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenbite.CartViewModel
import com.example.greenbite.MenuAdapter
import com.example.greenbite.MenuViewModel
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var menuAdapter: MenuAdapter
    private val menuViewModel: MenuViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel

        userViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.tvHomeName.text = "Welcome, ${user?.name ?: "Guest"}"
        }

        binding.btnMore.setOnClickListener(){
            findNavController().navigate(R.id.action_global_menuFragment)
        }

        binding.btnCartHome.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }

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
        binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMenu.adapter = menuAdapter
    }

    private fun observeMenuItems() {
        menuViewModel.topMenuItems.observe(viewLifecycleOwner) { menuItems ->
            menuAdapter.submitList(menuItems.toMutableList())
        }
    }
}