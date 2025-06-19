package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.R
import com.example.greenbite.User
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentAdminAddEmployeeBinding


class AdminAddEmployeeFragment : Fragment() {
    lateinit var binding: FragmentAdminAddEmployeeBinding
    private val viewModel: AdminViewModel by activityViewModels()
    private val userViewModel: UsersViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_employee, container, false)


        binding.btnConfirmAddEmpFrag.setOnClickListener(){
            val name = binding.txtNameAddEmpFrag.text.toString()
            val email = binding.txtEmailAddEmpFrag.text.toString()
            val role = binding.txtStatusAddEmpFrag.text.toString()

            val roleInt = when (role) {
                "Checker" -> 2
                "Kurir" -> 4
                else -> 1
            }

            val newUser = User(
                userID = null,
                name = name,
                email = email,
                password = "admin123",
                phone = "-",
                address = "-",
                postcode = "60235",
                pfp_url = "-",
                role = roleInt,
                credit = 0.0,
                deleted_at = null,
            )
            userViewModel.registerUser(newUser) { success, message ->
                activity?.runOnUiThread {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    if (success) {
                        findNavController().navigate(R.id.action_global_adminEmployeeFragment2)
                    }
                }
            }
        }
        binding.btnCancelAddEmpFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_global_adminEmployeeFragment2)
        }

        return binding.root
    }
}