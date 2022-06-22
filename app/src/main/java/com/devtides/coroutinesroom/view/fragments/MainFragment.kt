package com.devtides.coroutinesroom.view.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.AppDatabase
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.databinding.FragmentMainBinding
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        signoutBtn.setOnClickListener { onSignout() }
        deleteUserBtn.setOnClickListener { onDelete() }

        viewModel.signout.observe(viewLifecycleOwner, userDeletedObserver)
        viewModel.userDeleted.observe(viewLifecycleOwner, signoutObserver)

        viewModel.loadDatabase(AppDatabase(requireContext()).userDao())

        binding.usernameTV.text = LoginState.getUserName()
    }

    private val userDeletedObserver = Observer<Boolean> {
        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(usernameTV).navigate(action)
    }

    private val signoutObserver = Observer<Boolean> {
        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(usernameTV).navigate(action)
    }

    private fun onSignout() { viewModel.onSignout() }

    private fun onDelete() { viewModel.onDeleteUser() }

}
