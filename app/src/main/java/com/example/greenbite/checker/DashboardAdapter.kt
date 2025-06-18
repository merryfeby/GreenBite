package com.example.greenbite.checker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.databinding.RvDashboardCheckerBinding

class OrderDiffUtil: DiffUtil.ItemCallback<Order>(){

    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.orderID == newItem.orderID
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

val orderDiffUtil = OrderDiffUtil()
class DashboardAdapter(
): ListAdapter<Order, DashboardAdapter.ViewHolder>(orderDiffUtil) {
    class ViewHolder(val binding: RvDashboardCheckerBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardAdapter.ViewHolder {
        val binding = RvDashboardCheckerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardAdapter.ViewHolder, position: Int) {
        val order = getItem(position)
        holder.binding.tvCustnameRvdashboardChecker.text = order.userID.toString()
        holder.binding.tvDateRvdashboardChecker.text = order.created_at
        holder.binding.tvIdRvdashboardChecker.text = order.orderID.toString()
        holder.binding.tvSubAmountRvdashboardChecker.text = order.subtotal.toString()
        holder.binding.btnDetailRvdashboardChecker.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Menu added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}