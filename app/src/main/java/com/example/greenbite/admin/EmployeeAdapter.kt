package com.example.greenbite.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.ListEmployeeBinding
import com.example.greenbite.databinding.ListMenuBinding
import com.example.greenbite.R


class EmployeeDiffUtil: DiffUtil.ItemCallback<Employee>(){

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.userID == newItem.userID
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}

val employeeDiffUtil = EmployeeDiffUtil()

class EmployeeAdapter(
    private val usersViewModel: UsersViewModel,
    private val vm: AdminViewModel
) : ListAdapter<Employee, EmployeeAdapter.EmployeeViewHolder>(employeeDiffUtil) {

    class EmployeeViewHolder(val binding: ListEmployeeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ListEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.binding.txtNameRvEmp.text = employee.name
        holder.binding.txtDescRvEmp.text = employee.phone

        //edit
        holder.binding.imageButton.setOnClickListener(){
            vm.setActiveUser(employee)
            holder.itemView.findNavController().navigate(R.id.action_global_adminEditEmployee2)
        }
        //delete
        holder.binding.imageButton2.setOnClickListener(){
            usersViewModel.deleteUser(employee.userID!!)
        }
    }
}