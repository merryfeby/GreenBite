package com.example.greenbite.Customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.greenbite.R
import com.example.greenbite.databinding.FragmentMenuDetailBinding

class MenuDetailFragment : Fragment() {
    lateinit var binding : FragmentMenuDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_detail, container, false)

        return binding.root
    }

}