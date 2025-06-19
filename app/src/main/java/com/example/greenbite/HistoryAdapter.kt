package com.example.greenbite

import com.example.greenbite.databinding.ListHistoryBinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.checker.Order
import com.example.greenbite.databinding.ListMenuBinding
import java.text.NumberFormat
import java.util.Locale

class HistoryDiffUtil: DiffUtil.ItemCallback<Order>(){
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.orderID == newItem.orderID
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

val historyDiffUtil = HistoryDiffUtil()

class HistoryAdapter(
) : ListAdapter<Order, HistoryAdapter.ViewHolder>(historyDiffUtil) {

    class ViewHolder(val binding: ListHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = getItem(position)

        val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(history.total.toDouble())
        holder.binding.tvHistoryGrandtotal.text = "Rp $formattedPrice"
        holder.binding.tvHistoryItems.text = "${history.order_details.size} items"
        holder.binding.tvHistoryDatetime.text = history.created_at
        holder.binding.btnHistoryId.text = "Order #" + history.orderID.toString()
        holder.binding.btnHistoryDetails.setOnClickListener(){
            val bundle = Bundle().apply {
                putInt("orderID", history.orderID)
            }
            it.findNavController().navigate(R.id.action_global_customerDetailHistoryFragment, bundle)
        }
    }
}

