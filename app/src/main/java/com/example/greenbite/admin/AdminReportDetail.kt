package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.greenbite.R
import com.example.greenbite.databinding.FragmentAdminReportDetailBinding

class AdminReportDetail : Fragment() {
    lateinit var binding: FragmentAdminReportDetailBinding
//    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_report_detail, container, false)
        return binding.root
    }
}