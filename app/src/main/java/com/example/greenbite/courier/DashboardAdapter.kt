package com.example.greenbite.courier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.checker.Order
import com.example.greenbite.databinding.RvDashboardCheckerBinding
import com.example.greenbite.databinding.RvDashboardCourierBinding
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
    private val buttonLabel: String
): ListAdapter<Order, DashboardAdapter.ViewHolder>(orderDiffUtil) {
    var onDetailClickListener: ((Int)->Unit)? = null
    class ViewHolder(val binding: RvDashboardCourierBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardAdapter.ViewHolder {
        val binding = RvDashboardCourierBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardAdapter.ViewHolder, position: Int) {
        val order = getItem(position)
        holder.binding.btnDeliverRvdashboardCourier.text = buttonLabel
        holder.binding.tvCustnameRvdashboardCourier.text = order.customer_name
        holder.binding.tvAddressRvdashboardCourier.text = order.address
        holder.binding.tvDateRvdashboardCourier.text = order.created_at
        holder.binding.tvIdRvdashboardCourier.text = "# " + order.orderID.toString()
        holder.binding.btnDeliverRvdashboardCourier.setOnClickListener{
            onDetailClickListener?.invoke(order.orderID)
        }
    }
}