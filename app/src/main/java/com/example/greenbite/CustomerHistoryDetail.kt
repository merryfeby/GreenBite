package com.example.greenbite

import com.example.greenbite.databinding.ListHistorydetailBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.checker.OrderDetail  // Import OrderDetail, not Order
import java.text.NumberFormat
import java.util.Locale

class CustomerHistoryDiffUtil: DiffUtil.ItemCallback<OrderDetail>(){  // Change to OrderDetail
    override fun areItemsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
        return oldItem.orderDetailID == newItem.orderDetailID
    }

    override fun areContentsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
        return oldItem == newItem
    }
}

val customerhistoryDiffUtil = CustomerHistoryDiffUtil()

class CustomerHistoryAdapter : ListAdapter<OrderDetail, CustomerHistoryAdapter.ViewHolder>(customerhistoryDiffUtil) {  // Change to OrderDetail

    class ViewHolder(val binding: ListHistorydetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListHistorydetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyDetail = getItem(position)

        val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(historyDetail.total.toDouble())
        holder.binding.tvPrice.text = "Rp $formattedPrice"
        holder.binding.tvTitle.text = historyDetail.product_name
        holder.binding.tvItemCount.text = "${historyDetail.quantity} items"
    }
}