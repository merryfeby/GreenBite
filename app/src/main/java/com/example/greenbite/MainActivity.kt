package com.example.greenbite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.greenbite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavAdmin.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnReport_NavAdmin -> {
                    navController.navigate(R.id.action_global_adminReportFragment2)
                    true
                }
                R.id.btnProducts_NavAdmin -> {
                    navController.navigate(R.id.action_global_adminMenuFragment2)
                    true
                }
                R.id.btnEmployees_NavAdmin -> {
                    navController.navigate(R.id.action_global_adminEmployeeFragment2)
                    true
                }
                R.id.btnLogout_NavAdmin -> {
                    navController.navigate(R.id.loginFragment)
                    true
                }
                else -> false
            }
        }


        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.im_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.im_menu -> {
                    navController.navigate(R.id.menuFragment)
                    true
                }
                R.id.im_delivery -> {
                    navController.navigate(R.id.deliveryFragment)
                    true
                }
                R.id.im_history -> {
                    navController.navigate(R.id.historyFragment)
                    true
                }
                R.id.im_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.visibility = when (destination.id) {
                R.id.loginFragment, R.id.registerFragment, R.id.adminFragment3, R.id.adminEmployeeFragment2,R.id.adminMenuFragment2,R.id.adminAddEmployeeFragment2,R.id.adminAddMenuFragment2, R.id.adminEditEmployee2,R.id.adminEditMenuFragment2,R.id.adminReportFragment2,R.id.adminReportDetail -> View.GONE
                else -> View.VISIBLE
            }
            binding.bottomNavAdmin.visibility = when (destination.id) {
                R.id.adminFragment3, R.id.adminEmployeeFragment2,R.id.adminMenuFragment2,R.id.adminAddEmployeeFragment2,R.id.adminAddMenuFragment2, R.id.adminEditEmployee2,R.id.adminEditMenuFragment2,R.id.adminReportFragment2,R.id.adminReportDetail -> View.VISIBLE
                else -> View.GONE
            }
//            if (destination.id == R.id.adminEmployeeFragment2) {
//                binding.bottomNavAdmin.item
//            } else if (destination.id == R.id.adminMenuFragment2) {
//                binding.bottomNavAdmin.selectedItemId = 1
//            } else if (destination.id == R.id.adminReportFragment2) {
//                binding.bottomNavAdmin.selectedItemId = 2
//            }
        }


    }
}