package com.example.greenbite.Customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.R
import com.example.greenbite.User
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentEditProfileBinding


class EditProfileFragment : Fragment() {
    private val usersViewModel: UsersViewModel by activityViewModels()
    private lateinit var binding:FragmentEditProfileBinding
    private lateinit var postcodeAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.usersViewModel = usersViewModel

        binding.imgProfileBackBtn.setOnClickListener{
            findNavController().navigate(R.id.profileFragment)
        }

        binding.btnEditSave.setOnClickListener{
            saveChanges()
        }

        currentUserData()
        setupPostcodeSpinner()

        return binding.root
    }
    private fun setupPostcodeSpinner() {
        usersViewModel.fetchPostcodes()

        usersViewModel.postcodes.observe(viewLifecycleOwner) { postcodes ->
            val postcodeStrings = postcodes.map { it.postcodeID }
            postcodeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                postcodeStrings
            )
            postcodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.editProfilespinner.adapter = postcodeAdapter
        }
    }
    private fun currentUserData() {
        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.etEditName.setText(user!!.name)
            binding.etEditEmail.setText(user!!.email)
            binding.etEditPhone.setText(user!!.phone)
            binding.etEditAddress.setText(user!!.address)

           usersViewModel.postcodes.value?.let { postcodes ->
                val userPostcodeIndex = postcodes.indexOfFirst { it.postcodeID == user.postcode }
                if (userPostcodeIndex != -1) {
                    binding.editProfilespinner.setSelection(userPostcodeIndex)
                }
            }

            binding.etEditEmail.isEnabled = false
            binding.etEditEmail.alpha = 0.7f
        }
    }

    private fun saveChanges() {
        val currentUser = usersViewModel.activeUser.value ?: return

        val selectedPostcode = if (binding.editProfilespinner.selectedItem != null) {
            binding.editProfilespinner.selectedItem.toString()
        } else {
            currentUser.postcode
        }

        val updatedUser = User(
            userID = currentUser.userID,
            email = currentUser.email,
            password = if (binding.etEditPassword.text.toString().isNotEmpty())
                binding.etEditPassword.text.toString()
            else
                currentUser.password,
            name = if (binding.etEditName.text.toString().isNotEmpty())
                binding.etEditName.text.toString()
            else
                currentUser.name,
            phone = if (binding.etEditPhone.text.toString().isNotEmpty())
                binding.etEditPhone.text.toString()
            else
                currentUser.phone,
            address = if (binding.etEditAddress.text.toString().isNotEmpty())
                binding.etEditAddress.text.toString()
            else
                currentUser.address,
            postcode = selectedPostcode,
            role = currentUser.role
        )

        usersViewModel.updateUser(updatedUser)

        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.profileFragment)
    }
}