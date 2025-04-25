package com.example.greenbite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.btnRegister1.setOnClickListener(){
            findNavController().navigate(R.id.action_global_registerFragment)
        }
        binding.btnLogin1.setOnClickListener(){
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()

            if (email == "" || password == ""){
                Toast.makeText(context, "fields must be filled !", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.setActiveUser(email)
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        return binding.root
    }

}