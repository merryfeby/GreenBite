package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenbite.R
import com.example.greenbite.UserViewModel
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentAdminEmployeeBinding
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class AdminEmployeeFragment : Fragment() {
    lateinit var binding: FragmentAdminEmployeeBinding
    private val viewModel: AdminViewModel by activityViewModels()
    lateinit var employeeAdapter: EmployeeAdapter
    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_employee, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getEmployees()
//            employeeAdapter.notifyDataSetChanged()
        }
        employeeAdapter = EmployeeAdapter(usersViewModel,viewModel)
        binding.rvEmployeeAdminFrag.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEmployeeAdminFrag.adapter = employeeAdapter

        viewModel.employees.observe(viewLifecycleOwner) { employees ->
            if (employees != null) {
                val activeEmployees = employees.filter { it.deleted_at == null }
                employeeAdapter.submitList(activeEmployees)
            }
        }

        binding.btnAddEmployeeFrag.setOnClickListener(){
            findNavController().navigate(R.id.action_global_adminAddEmployeeFragment2)
        }
        binding.btnRefreshEmployeeFrag.setOnClickListener(){
            lifecycleScope.launch {
                viewModel.getEmployees()
            }
        }
    }
}


