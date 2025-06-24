package com.example.greenbite

import com.example.greenbite.databinding.ListDeliveryBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.checker.Order
import java.text.NumberFormat
import java.util.Locale


class DeliveryDiffUtil: DiffUtil.ItemCallback<Order>(){
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.orderID == newItem.orderID
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

val deliveryDiffUtil = DeliveryDiffUtil()

class DeliveryAdapter(
) : ListAdapter<Order, DeliveryAdapter.ViewHolder>(deliveryDiffUtil) {

    class ViewHolder(val binding: ListDeliveryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDeliveryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val delivery = getItem(position)

        val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(delivery.total.toDouble())
        holder.binding.tvOrderPrice.text = "Rp $formattedPrice"
        holder.binding.tvOrderNumber.text = "Order #" + delivery.orderID.toString()
        holder.binding.tvOrderDate.text = delivery.created_at
        holder.binding.tvItemCount.text = "${delivery.order_details.size} items"
        holder.binding.tvStatus.text = "${delivery.status} - ${delivery.prep_time + 20} Minutes"
    }
}

