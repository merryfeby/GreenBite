package com.example.greenbite.Customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.greenbite.App
import com.example.greenbite.CartEntity
import com.example.greenbite.CartViewModel
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentMenuDetailBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MenuDetailFragment : Fragment() {
    lateinit var binding: FragmentMenuDetailBinding
    private val productViewModel: ProductViewModel by activityViewModels()
    private val userViewModel: UsersViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private var selectedSize = ""
    private var selectedSugar = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_detail, container, false)

        val productId = arguments?.getInt("productID") ?: 0
        Log.d("MenuDetailFragment", "Attempting to fetch product with ID: $productId")
        if (productId != 0) {
            productViewModel.fetchProductDetail(productId)
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.productViewModel = productViewModel
        binding.usersViewModel = userViewModel

        binding.menuDetailBackBtn.setOnClickListener(){
            findNavController().popBackStack()
        }

        setupObservers()
        setupClickListeners()
        return binding.root
    }

    private fun setupObservers() {
        productViewModel.selectedProduct.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.tvMenuName.text = it.name
                binding.tvCategoryMenu.text = it.category.name
                binding.tvRatingDetailMenu.text = it.rating.toString()
                binding.tvMenuDesc.text = it.description

                val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(it.price.toDouble())
                binding.tvPrice.text = "Rp$formattedPrice"

                val isBeverage = it.category.name.equals("Beverages", ignoreCase = true)
                toggleBeverageOptions(isBeverage)
            }
        }
    }

    private fun toggleBeverageOptions(isBeverage: Boolean) {
        val visibility = if (isBeverage) View.VISIBLE else View.GONE

        binding.btnSizeS?.visibility = visibility
        binding.btnSizeM?.visibility = visibility
        binding.btnSizeL?.visibility = visibility

        binding.tvSugar?.visibility = visibility
        binding.tvSize?.visibility = visibility
        binding.btnNormalSugar?.visibility = visibility
        binding.btnLessSugar?.visibility = visibility
        binding.btnNoSugar?.visibility = visibility

        if (isBeverage) {
            selectSize("")
            selectSugar("")
        }
    }

    private fun setupClickListeners() {
        binding.btnSizeS?.setOnClickListener { selectSize("S") }
        binding.btnSizeM?.setOnClickListener { selectSize("M") }
        binding.btnSizeL?.setOnClickListener { selectSize("L") }

        binding.btnNormalSugar?.setOnClickListener { selectSugar("Normal") }
        binding.btnLessSugar?.setOnClickListener { selectSugar("Less Sugar") }
        binding.btnNoSugar?.setOnClickListener { selectSugar("No Sugar") }

        binding.detailMenuAddBtn?.setOnClickListener {
            addToCart()
        }
    }

    private fun selectSize(size: String) {
        selectedSize = size

        resetSizeButtonStyle(binding.btnSizeS)
        resetSizeButtonStyle(binding.btnSizeM)
        resetSizeButtonStyle(binding.btnSizeL)

        when (size) {
            "S" -> setSelectedButtonStyle(binding.btnSizeS)
            "M" -> setSelectedButtonStyle(binding.btnSizeM)
            "L" -> setSelectedButtonStyle(binding.btnSizeL)
        }
    }

    private fun selectSugar(sugar: String) {
        selectedSugar = sugar

        resetSugarButtonStyle(binding.btnNormalSugar)
        resetSugarButtonStyle(binding.btnLessSugar)
        resetSugarButtonStyle(binding.btnNoSugar)

        when (sugar) {
            "Normal" -> setSelectedButtonStyle(binding.btnNormalSugar)
            "Less Sugar" -> setSelectedButtonStyle(binding.btnLessSugar)
            "No Sugar" -> setSelectedButtonStyle(binding.btnNoSugar)
        }
    }

    private fun setSelectedButtonStyle(button: View?) {
        button?.let {
            it.isSelected = true
            if (it is android.widget.Button) {
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.Lightgreen))
            }
        }
    }

    private fun resetSizeButtonStyle(button: View?) {
        button?.let {
            it.isSelected = false
            if (it is android.widget.Button) {
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun resetSugarButtonStyle(button: View?) {
        button?.let {
            it.isSelected = false
            if (it is android.widget.Button) {
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun addToCart() {
        val product = productViewModel.selectedProduct.value ?: return
        val userEmail = userViewModel.activeUser.value?.email ?: "guest"

        if (product.category.name.equals("Beverages", ignoreCase = true)) {
            if (selectedSize.isEmpty()) {
                Toast.makeText(context, "Please select a size", Toast.LENGTH_SHORT).show()
                return
            }
            if (selectedSugar.isEmpty()) {
                Toast.makeText(context, "Please select sugar preference", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val addOn = if (product.category.name.equals("Beverages", ignoreCase = true)) {
            "Size: $selectedSize, Sugar: $selectedSugar"
        } else {
            ""
        }

        val cartItem = CartEntity(
            user_email = userEmail,
            id_menu = product.productID,
            nama_menu = product.name,
            harga = product.price.toDouble(),
            jumlah = 1,
            add_on = addOn
        )

        cartViewModel.addToCart(cartItem)

        val itemName = product.name
        val addOnText = if (addOn.isNotEmpty()) " ($addOn)" else ""
        Toast.makeText(
            context,
            "$itemName added to cart!",
            Toast.LENGTH_SHORT
        ).show()
    }

}