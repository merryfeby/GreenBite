package com.example.greenbite.courier

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.greenbite.R
import com.example.greenbite.databinding.ActivityCourierBinding

class CourierActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCourierBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_courier)
    }
}