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
import com.example.greenbite.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        viewModel.init()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.buttonRegister1.setOnClickListener(){
            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val phone = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()
            val postcode = binding.etPasscode.text.toString()

            if (email == "" || name == "" || password == "" || phone == "" || address == "" || postcode == ""){
                Toast.makeText(context, "field should be filled !", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.checkEmailExists(email) { exists ->
                if (exists) {
                    Toast.makeText(context, "Email already registered!", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = UserEntity(email, name, password, phone, address, postcode)
                    viewModel.insertUser(newUser)

                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_global_loginFragment)
                }
            }
        }

        binding.TvLogin.setOnClickListener(){
            findNavController().navigate(R.id.action_global_loginFragment)
        }

        return binding.root
    }
}