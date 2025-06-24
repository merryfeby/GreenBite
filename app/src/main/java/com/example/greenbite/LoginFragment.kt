package com.example.greenbite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.checker.CheckerActivity
import com.example.greenbite.courier.CourierActivity
import com.example.greenbite.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.tvRegister.setOnClickListener(){
            findNavController().navigate(R.id.action_global_registerFragment)
        }

        binding.btnLogin1.setOnClickListener(){
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(context, "All fields must be filled!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            viewModel.loginUser(email, password) { success, message, user ->
                if (success && user != null) {
                    Toast.makeText(context, "Welcome ${user.name}!", Toast.LENGTH_SHORT).show()

                    when (user.role) {
                        1 -> { // Customer
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        2 -> { // Checker
                            val intent = Intent(requireContext(), CheckerActivity::class.java)
                            intent.putExtra("userEmail", user.email)
                            startActivity(intent)
                        }
                        3 -> { // Admin
                            findNavController().navigate(R.id.action_global_adminFragment3)
                        }
                        4 -> {
                            val intent = Intent(requireContext(), CourierActivity::class.java)
                            intent.putExtra("userEmail", user.email)
                            startActivity(intent)
                        }
                        else -> {
                            Toast.makeText(context, "Unknown user role", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }
}