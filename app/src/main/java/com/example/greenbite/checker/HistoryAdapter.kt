package com.example.greenbite.checker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.databinding.RvOrderdetailHistoryCheckerBinding
import java.text.NumberFormat
import java.util.Locale

class OrderDetailDiffUtil: DiffUtil.ItemCallback<OrderDetail>(){
    override fun areItemsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
        return oldItem.orderDetailID == newItem.orderDetailID
    }

    override fun areContentsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
        return oldItem == newItem
    }

}

val orderDetailDiffUtil = OrderDetailDiffUtil()
class HistoryAdapter(

): ListAdapter<OrderDetail, HistoryAdapter.ViewHolder>(orderDetailDiffUtil){
    class ViewHolder(val binding: RvOrderdetailHistoryCheckerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = RvOrderdetailHistoryCheckerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val orderDetail = getItem(position)
        val formattedAmount = NumberFormat.getNumberInstance(Locale("in", "ID")).format(orderDetail.total)
        holder.binding.tvQtyItemDetailChecker.text = orderDetail.quantity.toString()
        holder.binding.tvNameItemDetailChecker.text = orderDetail.product_name
        holder.binding.tvPriceItemDetailChecker.text = "Rp."+formattedAmount+",00"
    }

}