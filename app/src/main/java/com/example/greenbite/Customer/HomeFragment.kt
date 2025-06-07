package com.example.greenbite.Customer

import android.os.Bundle
import android.util.Log
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
import com.example.greenbite.Product
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UsersViewModel by activityViewModels()
    private lateinit var menuAdapter: MenuAdapter
    private val menuViewModel: MenuViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.usersViewModel = userViewModel

        // Add debug logging
        Log.d("HomeFragment", "onCreateView called")

        userViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            Log.d("HomeFragment", "User observed: $user")
            if (user != null) {
                Log.d("HomeFragment", "User name: ${user.name}")
                binding.tvHomeName.text = "Welcome, ${user.name}"
            } else {
                Log.d("HomeFragment", "User is null")
                binding.tvHomeName.text = "Welcome, Guest"
            }
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

        val currentUser = userViewModel.activeUser.value
        Log.d("HomeFragment", "Current user in onViewCreated: $currentUser")
    }

    private fun setupRecyclerView() {
        val userEmail = userViewModel.activeUser.value?.email ?: "guest"
        Log.d("HomeFragment", "Setting up RecyclerView with user email: $userEmail")
        menuAdapter = MenuAdapter(cartViewModel, userEmail)
        binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMenu.adapter = menuAdapter
    }
}