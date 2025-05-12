package com.example.greenbite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.databinding.ListMenuBinding

class MenuDiffUtil: DiffUtil.ItemCallback<MenuEntity>(){
    override fun areItemsTheSame(oldItem: MenuEntity, newItem: MenuEntity): Boolean {
        return oldItem.id_menu == newItem.id_menu
    }

    override fun areContentsTheSame(oldItem: MenuEntity, newItem: MenuEntity): Boolean {
        return oldItem == newItem
    }
}

val menuDiffUtil = MenuDiffUtil()

class MenuAdapter : ListAdapter<MenuEntity, MenuAdapter.ViewHolder>(menuDiffUtil) {
    class ViewHolder(val binding: ListMenuBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = getItem(position)
        holder.binding.rvNamaMenu.text = menu.nama_menu
        holder.binding.rvRatingMenu.text = menu.rating_menu.toString()
        holder.binding.rvHargaMenu.text = "Rp ${menu.price_menu}"
    }
}
