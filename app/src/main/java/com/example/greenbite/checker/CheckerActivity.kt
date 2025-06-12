package com.example.greenbite.checker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.greenbite.R
import com.example.greenbite.databinding.ActivityCheckerBinding

class CheckerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCheckerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checker)
    }
}