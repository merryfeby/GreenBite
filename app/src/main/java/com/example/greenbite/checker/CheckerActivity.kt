package com.example.greenbite.checker

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.greenbite.R
import com.example.greenbite.databinding.ActivityCheckerBinding

class CheckerActivity : AppCompatActivity() {
    private val vm: CheckerViewModel by viewModels()
    private lateinit var binding : ActivityCheckerBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checker)
        val userEmail = intent.getStringExtra("userEmail")!!
        vm.init(userEmail)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navhost_checker) as NavHostFragment
        navController = navHostFragment.navController


        binding.bottomnavChecker.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_mi_checker -> {
                    navController.navigate(R.id.checkerDashboardFragment)
                    true
                }
                R.id.history_mi_checker -> {
                    navController.navigate(R.id.checkerHistoryFragment)
                    true
                }
                else -> false
            }
        }
    }
}