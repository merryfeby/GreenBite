package com.example.greenbite.Customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.CartAdapter
import com.example.greenbite.Table.CartViewModel
import com.example.greenbite.R
import com.example.greenbite.Table.UserViewModel
import com.example.greenbite.databinding.FragmentCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.cartViewModel = cartViewModel


        binding.btnCartBack.setOnClickListener(){
            findNavController().navigate(R.id.action_global_homeFragment)
        }

        binding.btnOrder.setOnClickListener(){

        }


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCartItems()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartViewModel)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = cartAdapter
    }

    private fun observeCartItems() {
        val userEmail = userViewModel.activeUser.value?.email ?: "guest"
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

        cartViewModel.getCartByUser(userEmail).observe(viewLifecycleOwner) { cartItems ->
            if (cartItems.isNotEmpty()) {
                binding.tvCartEmpty.visibility = View.GONE
                binding.rvCart.visibility = View.VISIBLE
                cartAdapter.submitList(cartItems)
                cartViewModel.calculateCartTotal(cartItems)
                formatter.maximumFractionDigits = 0
                binding.tvTotal.text = formatter.format(cartViewModel.cartTotal.value ?: 0.0)

            } else {
                binding.tvCartEmpty.visibility = View.VISIBLE
                binding.rvCart.visibility = View.GONE
            }
        }

        cartViewModel.cartTotal.observe(viewLifecycleOwner) { total ->
            formatter.maximumFractionDigits = 0
            binding.tvSubtotal.text = formatter.format(total)
        }
    }
}