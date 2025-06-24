package com.example.greenbite.Customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.greenbite.App
import com.example.greenbite.OrderViewModel
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.Rating
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentCustomerRatingBinding
import kotlinx.coroutines.launch

class CustomerRatingFragment : Fragment() {
    lateinit var binding : FragmentCustomerRatingBinding
    private val usersViewModel: UsersViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    private var productID: Int = 0
    private var orderDetailID: Int = 0
    private var userID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_customer_rating, container, false)

        binding.usersViewModel = usersViewModel
        binding.orderViewModel = orderViewModel
        binding.productViewModel = productViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            productID = it.getInt("productID", 0)
            orderDetailID = it.getInt("orderDetailID", 0)
        }

        userID = usersViewModel.activeUser.value?.userID ?: 0

        if (userID == 0) {
            Toast.makeText(requireContext(), "User not logged in.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return binding.root
        }

        binding.btnBackRating.setOnClickListener(){
            findNavController().popBackStack()
        }

        binding.btnRatingSubmit.setOnClickListener {
            val ratingText = binding.etRating.text.toString()
            val ratingValue = ratingText.toIntOrNull()

            if (ratingValue == null || ratingValue !in 1..5) {
                Toast.makeText(requireContext(), "Please enter a valid rating (1-5)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rating = Rating(
                ratingID = 0,
                userID = userID,
                productID = productID,
                orderDetailID = orderDetailID,
                rating = ratingValue,
                created_at = "",
                updated_at = ""
            )

            lifecycleScope.launch {
                try {
                    productViewModel.addRating(rating)
                    Toast.makeText(requireContext(), "Thank you for your rating!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } catch (e: Exception) {
                    Log.e("RatingError", "Failed to submit rating: ${e.localizedMessage}")
                    Toast.makeText(requireContext(), "Failed to submit rating. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}