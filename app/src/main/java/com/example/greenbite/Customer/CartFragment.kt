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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.CartAdapter
import com.example.greenbite.CartViewModel
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by activityViewModels()
    private val usersViewModel: UsersViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cartViewModel = cartViewModel
        binding.usersViewModel = usersViewModel

        val userID = usersViewModel.activeUser.value?.userID ?: 0
        val userEmail = usersViewModel.activeUser.value?.email ?: ""

        binding.btnCartBack.setOnClickListener(){
            findNavController().navigate(R.id.action_global_homeFragment)
        }

        binding.btnOrder.setOnClickListener(){
//            cartViewModel.createOrder(userID, userEmail)
        }

        setupRecyclerView()
        observeCartItems()

    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartViewModel)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = cartAdapter
    }

    private fun observeCartItems() {
        val userEmail = usersViewModel.activeUser.value?.email ?: "guest"
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

        val postcode = usersViewModel.activeUser.value?.postcode ?: ""

        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.postcode?.let { postcode ->
                cartViewModel.calculateDeliveryFee(postcode)
            }
        }

        cartViewModel.deliveryFee.observe(viewLifecycleOwner) { fee ->
            formatter.maximumFractionDigits = 0
            binding.tvDeliveryFee.text = formatter.format(fee)
        }


        cartViewModel.getCartByUser(userEmail).observe(viewLifecycleOwner) { cartItems ->
            if (cartItems.isNotEmpty()) {
                binding.tvCartEmpty.visibility = View.GONE
                binding.rvCart.visibility = View.VISIBLE
                cartAdapter.submitList(cartItems)
                cartViewModel.calculateCartTotal(cartItems)
                cartViewModel.calculateGrandTotal(cartItems, postcode)
                formatter.maximumFractionDigits = 0
                binding.rvCart.visibility = View.VISIBLE

            } else {
                binding.tvCartEmpty.visibility = View.VISIBLE
                binding.rvCart.visibility = View.GONE
            }
        }

        cartViewModel.cartTotal.observe(viewLifecycleOwner) { subtotal ->
            formatter.maximumFractionDigits = 0
            binding.tvSubtotal.text = formatter.format(subtotal)
        }

        cartViewModel.grandTotal.observe(viewLifecycleOwner) { total ->
            formatter.maximumFractionDigits = 0
            binding.tvTotal.text = formatter.format(total)
        }
    }
}