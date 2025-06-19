package com.example.greenbite.checker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.databinding.RvDashboardCheckerBinding
import java.text.NumberFormat
import java.util.Locale

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
    var onDetailClickListener: ((Int)->Unit)? = null
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
        val formattedAmount = NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.total)
        holder.binding.tvCustnameRvdashboardChecker.text = order.customer_name
        holder.binding.tvDateRvdashboardChecker.text = order.created_at
        holder.binding.tvIdRvdashboardChecker.text = "# " + order.orderID.toString()
        holder.binding.tvSubAmountRvdashboardChecker.text = "Rp."+formattedAmount+",00"
        holder.binding.btnDetailRvdashboardChecker.setOnClickListener{
            onDetailClickListener?.invoke(order.orderID)
        }
    }
}