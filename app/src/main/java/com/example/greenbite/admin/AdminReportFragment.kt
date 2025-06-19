package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.greenbite.R
import com.example.greenbite.databinding.FragmentAdminReportBinding

class AdminReportFragment : Fragment() {
    lateinit var binding: FragmentAdminReportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_report, container, false)
        return binding.root
    }
}