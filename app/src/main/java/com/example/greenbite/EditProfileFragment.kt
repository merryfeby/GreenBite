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
import com.example.greenbite.databinding.FragmentEditProfileBinding


class EditProfileFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var binding:FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel

        binding.imgProfileBackBtn.setOnClickListener{
            findNavController().navigate(R.id.profileFragment)
        }

        binding.btnEditSave.setOnClickListener{
            saveChanges()
        }

        currentUserData()



        return binding.root
    }

    private fun currentUserData() {
        userViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.etEditName.setText(user.name)
            binding.etEditEmail.setText(user.email)
            binding.etEditPhone.setText(user.phone)
            binding.etEditAddress.setText(user.address)
            binding.etEditPosscode.setText(user.postcode)

            binding.etEditEmail.isEnabled = false
            binding.etEditEmail.alpha = 0.7f
        }
    }

    private fun saveChanges() {
        val currentUser = userViewModel.activeUser.value ?: return

        val updatedUser = UserEntity(
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
            postcode = if (binding.etEditPosscode.text.toString().isNotEmpty())
                binding.etEditPosscode.text.toString()
            else
                currentUser.postcode
        )

        userViewModel.updateUser(updatedUser)

        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.profileFragment)
    }
}