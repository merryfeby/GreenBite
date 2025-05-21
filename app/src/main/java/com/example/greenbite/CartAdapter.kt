package com.example.greenbite

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.databinding.ListCartBinding
import android.view.LayoutInflater
import com.example.greenbite.Table.CartEntity
import com.example.greenbite.Table.CartViewModel
import java.text.NumberFormat
import java.util.Locale

class CartDiffUtil: DiffUtil.ItemCallback<CartEntity>(){
    override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
        return oldItem.id_cart == newItem.id_cart
    }

    override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
        return oldItem == newItem
    }
}

val cartDiffUtil = CartDiffUtil()

class CartAdapter(private val cartViewModel: CartViewModel) : ListAdapter<CartEntity, CartAdapter.ViewHolder>(cartDiffUtil) {
    class ViewHolder(val binding: ListCartBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = getItem(position)
        val totalPrice = cart.harga * cart.jumlah
        val formattedTotalPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(totalPrice)

        holder.binding.apply {
            itemName.text = cart.nama_menu
            itemPrice.text = "Rp $formattedTotalPrice"
            quantityText.text = cart.jumlah.toString()

            btnTambahStok.setOnClickListener {
                cartViewModel.updateCartItemQuantity(cart, cart.jumlah + 1)
            }

            btnKurangStok.setOnClickListener {
                if (cart.jumlah > 1) {
                    cartViewModel.updateCartItemQuantity(cart, cart.jumlah - 1)
                } else if (cart.jumlah == 1) {
                    btnKurangStok.isEnabled = false
                }
            }

            cartDeleteBtn.setOnClickListener {
                cartViewModel.removeFromCart(cart.user_email, cart.id_menu)
            }
        }
    }

}