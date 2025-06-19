package com.example.greenbite.checker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.R
import com.example.greenbite.databinding.FragmentCheckerDetailBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CheckerDetailFragment : Fragment() {
    private val checkerViewModel: CheckerViewModel by activityViewModels()
    lateinit var binding: FragmentCheckerDetailBinding
    lateinit var adapter: OrderDetailAdapter
    private var time = 0
    val format = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.ENGLISH).apply {
        timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checker_detail, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkerViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtWelcomeDetailChecker.text = "Welcome, ${it.name}"
            }
        }
        checkerViewModel.activeOrder.observe(viewLifecycleOwner) { order ->
            order?.let {
                val now = Date()
                binding.txtCustDetailChecker.text = order.customer_name
                binding.txtIdDetailChecker.text = "#" + order.orderID
                binding.txtPlacedDateDetailChecker.text = order.created_at
                binding.txtNameDetailChecker.text = order.customer_name
                binding.txtPhoneDetailChecker.text = order.customer_phone
                binding.txtSubtotalDetailChecker.text = "Rp."+NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.subtotal)
                binding.txtGrandtotalDetailChecker.text = "Rp."+NumberFormat.getNumberInstance(Locale("in", "ID")).format(order.total)
                val inputDate: Date? = format.parse(order.created_at)
                if (inputDate != null) {
                    val diffMillis = now.time - inputDate.time
                    val diffMinutes = diffMillis / (60 * 1000)
                    binding.txtTimeDetailChecker.text = "Placed $diffMinutes min ago"
                } else {
                    binding.txtTimeDetailChecker.text = "Placed unknown time"
                }
            }
        }
        adapter = OrderDetailAdapter()
        binding.rvOrderDetailChecker.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrderDetailChecker.adapter = adapter
        checkerViewModel.activeOrder.observe(viewLifecycleOwner){ orders ->
            if (orders != null){
                adapter.submitList(orders.order_details)
            }
        }
        time = 0
        binding.btnMinusDetailChecker.setOnClickListener{
            if (time > 0){
                time--
            }
            binding.etTimeDetailChecker.setText(time.toString())
        }
        binding.btnPlusDetailChecker.setOnClickListener{
            time++
            binding.etTimeDetailChecker.setText(time.toString())
        }
        binding.btnAcceptDetailChecker.setOnClickListener{
            val prep_time = binding.etTimeDetailChecker.text.toString().toInt()
            checkerViewModel.acceptOrder(prep_time)
            findNavController().navigate(R.id.action_global_checkerDashboardFragment)
        }
        binding.btnRejectDetailChecker.setOnClickListener{
            checkerViewModel.rejectOrder()
            findNavController().navigate(R.id.action_global_checkerDashboardFragment)
        }
    }
}