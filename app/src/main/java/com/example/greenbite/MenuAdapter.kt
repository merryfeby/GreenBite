package com.example.greenbite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.databinding.ListMenuBinding
import java.text.NumberFormat
import java.util.Locale

class MenuDiffUtil: DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productID == newItem.productID
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}

val menuDiffUtil = MenuDiffUtil()

class MenuAdapter(
    private val cartViewModel: CartViewModel,
    private val userEmail: String
) : ListAdapter<Product, MenuAdapter.ViewHolder>(menuDiffUtil) {

    class ViewHolder(val binding: ListMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = getItem(position)

        val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(menu.price.toDouble())
        holder.binding.tvProductNameMenu.text = menu.name
        holder.binding.tvRatingMenu.text = menu.rating.toString()
        holder.binding.tvPriceMenu.text = "Rp $formattedPrice"
        holder.binding.tvCategoryNameMenu.text = menu.category.name
        holder.binding.tvTotalRating.text = "${menu.total_rating} Ratings"

        holder.binding.btnAddMenu.setOnClickListener {
            val cartItem = CartEntity(
                user_email = userEmail,
                id_menu = menu.productID,
                nama_menu = menu.name,
                harga = menu.price.toDouble(),
                jumlah = 1
            )
            cartViewModel.addToCart(cartItem)
            Toast.makeText(holder.itemView.context, "Menu added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}

