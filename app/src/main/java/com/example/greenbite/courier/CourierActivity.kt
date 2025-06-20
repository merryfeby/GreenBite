package com.example.greenbite.courier

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.greenbite.R
import com.example.greenbite.databinding.ActivityCourierBinding

class CourierActivity : AppCompatActivity() {
    private val vm: CourierViewModel by viewModels()
    private lateinit var binding : ActivityCourierBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_courier)
        val userEmail = intent.getStringExtra("userEmail")!!
        vm.init(userEmail)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navhost_courier) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomnavCourier.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.home_mi_courier -> {
                    navController.navigate(R.id.courierDashboardFragment)
                    true
                }
                R.id.delivering_mi_courier -> {
                    navController.navigate(R.id.courierDeliveryFragment)
                    true
                }
                R.id.history_mi_courier -> {
                    navController.navigate(R.id.courierHistoryFragment)
                    true
                }
                else -> false
            }
        }
    }
}