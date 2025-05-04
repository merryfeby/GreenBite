package com.example.greenbite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenbite.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var menuAdapter: MenuAdapter
    private val menuViewModel: MenuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel

        userViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.tvHomeName.text = "Welcome, ${user?.name ?: "Guest"}"
        }

        binding.btnMore.setOnClickListener(){

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMenuItems()
    }

    private fun setupRecyclerView() {
        menuAdapter = MenuAdapter()
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = menuAdapter
        }
    }

    private fun observeMenuItems() {
        menuViewModel.topMenuItems.observe(viewLifecycleOwner) { menuItems ->
            Log.d("HomeFragment", "Menu items received: ${menuItems.size}")
            menuAdapter.submitList(menuItems)
        }
    }
}