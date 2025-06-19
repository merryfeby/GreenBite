package com.example.greenbite.admin

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.Product
import com.example.greenbite.ProductViewModel
import com.example.greenbite.R
import com.example.greenbite.checker.Order
import com.example.greenbite.checker.orderDiffUtil
import com.example.greenbite.databinding.ListMenuAdminBinding
import com.example.greenbite.databinding.RvDashboardCheckerBinding
import com.example.greenbite.databinding.RvReportBinding

class OrderDiffUtil: DiffUtil.ItemCallback<Order>(){

    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.orderID == newItem.orderID
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

val orderDiffUtil = OrderDiffUtil()
class ReportAdapter(
) : ListAdapter<Order, ReportAdapter.ReportViewHolder>(orderDiffUtil) {
    class ReportViewHolder(val binding: RvReportBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = RvReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = getItem(position)
        holder.binding.txtIDRvReport.text = report.orderID.toString()
        holder.binding.txtDateRvReport.text = report.created_at
        holder.binding.txtTotalRvReport.text = "Total: Rp."+report.total.toString()
        holder.binding.txtOngkirRvReport.text = "Ongkir: Rp."+report.shipping_fee.toString()
        holder.binding.txtSubtotalRvReport.text = "Subtotal: Rp."+report.subtotal.toString()
        holder.binding.btnStatus.setText(report.status.toString())

        if (report.status == "completed") {
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green2))
        }else if (report.status == "pending") {
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
        }else if (report.status == "shipped") {
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
        }else if (report.status == "rejected") {
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        }else if (report.status == "cooking") {
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))

        }
    }
}