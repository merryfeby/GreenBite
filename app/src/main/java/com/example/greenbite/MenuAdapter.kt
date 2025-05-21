package com.example.greenbite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.Table.CartEntity
import com.example.greenbite.Table.CartViewModel
import com.example.greenbite.Table.MenuEntity
import com.example.greenbite.databinding.ListMenuBinding
import java.text.NumberFormat
import java.util.Locale

class MenuDiffUtil: DiffUtil.ItemCallback<MenuEntity>(){
    override fun areItemsTheSame(oldItem: MenuEntity, newItem: MenuEntity): Boolean {
        return oldItem.id_menu == newItem.id_menu
    }

    override fun areContentsTheSame(oldItem: MenuEntity, newItem: MenuEntity): Boolean {
        return oldItem == newItem
    }
}

val menuDiffUtil = MenuDiffUtil()

class MenuAdapter(
    private val cartViewModel: CartViewModel,
    private val userEmail: String
) : ListAdapter<MenuEntity, MenuAdapter.ViewHolder>(menuDiffUtil) {

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

        val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(menu.price_menu)
        holder.binding.rvNamaMenu.text = menu.nama_menu
        holder.binding.rvRatingMenu.text = menu.rating_menu.toString()
        holder.binding.rvHargaMenu.text = "Rp $formattedPrice"

        holder.binding.btnAddMenu.setOnClickListener {
            val cartItem = CartEntity(
                user_email = userEmail,
                id_menu = menu.id_menu,
                nama_menu = menu.nama_menu,
                harga = menu.price_menu.toDouble(),
                jumlah = 1
            )
            cartViewModel.addToCart(cartItem)
            Toast.makeText(holder.itemView.context, "Menu added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}

