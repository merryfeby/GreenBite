package com.example.greenbite.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.Product
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.ListEmployeeBinding
import com.example.greenbite.databinding.ListMenuAdminBinding

class ProductDiffUtil: DiffUtil.ItemCallback<Product>(){

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productID == newItem.productID
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
val productDiffUtil = ProductDiffUtil()

class ProductAdapter(
//    private val usersViewModel: UsersViewModel
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(productDiffUtil) {

    class ProductViewHolder(val binding: ListMenuAdminBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ListMenuAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.binding.rvNamaMenu.text = product.name
        holder.binding.rvHargaMenu.text = product.price.toString()
        holder.binding.rvRatingMenu.text = product.rating.toString()
        holder.binding.rvRatingMenu3.text = "0"
        holder.binding.btnEditRvMenuAdmin.setOnClickListener {
            // Handle edit button click
        }
    }
}