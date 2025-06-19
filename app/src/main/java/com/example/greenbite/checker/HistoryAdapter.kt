package com.example.greenbite.checker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.checker.DashboardAdapter.ViewHolder
import com.example.greenbite.databinding.RvDashboardCheckerBinding
import com.example.greenbite.databinding.RvHistoryCheckerBinding
import java.text.NumberFormat
import java.util.Locale

class HistoryAdapter(

): ListAdapter<Order, HistoryAdapter.ViewHolder>(orderDiffUtil) {
    var onDetailClickListener: ((Int)->Unit)? = null
    class ViewHolder(val binding: RvHistoryCheckerBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvHistoryCheckerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)
        val formattedAmount = NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.total)
        holder.binding.tvCustnameRvhistoryChecker.text = order.customer_name
        holder.binding.tvDateRvhistoryChecker.text = order.created_at
        holder.binding.tvIdRvhistoryChecker.text = "# " + order.orderID.toString()
        holder.binding.tvSubAmountRvhistoryChecker.text = "Rp."+formattedAmount+",00"
        holder.binding.btnDetailRvhistoryChecker.setOnClickListener{
            onDetailClickListener?.invoke(order.orderID)
        }
        holder.binding.tvStatusRvhistoryChecker.text = order.status
        if (order.status == "completed"){
            holder.binding.tvStatusRvhistoryChecker.setTextColor(Color.parseColor("#33C98B"))
        }else if (order.status == "cooking"){
            holder.binding.tvStatusRvhistoryChecker.setTextColor(Color.parseColor("#FF9800"))
        }else if (order.status == "rejected"){
            holder.binding.tvStatusRvhistoryChecker.setTextColor(Color.parseColor("#F44336"))
        }else if (order.status == "shipping"){
            holder.binding.tvStatusRvhistoryChecker.setTextColor(Color.parseColor("#2196F3"))
        }
    }
}