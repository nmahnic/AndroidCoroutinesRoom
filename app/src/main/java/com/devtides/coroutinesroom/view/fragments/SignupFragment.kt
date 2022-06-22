package com.devtides.coroutinesroom.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.AppDatabase
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.databinding.FragmentSignupBinding
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val viewModel: SignupViewModel by viewModels()
    private lateinit var binding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignupBinding.bind(view)

        signupBtn.setOnClickListener { onSignup(it) }
        gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        signupCompleteObserver.view = view
        viewModel.signupComplete.observe(viewLifecycleOwner, signupCompleteObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)

        viewModel.loadDatabase(AppDatabase(requireContext()).userDao())
    }

    private val signupCompleteObserver = object : Observer<Boolean> {
        var view : View? = null

        override fun onChanged(isComplete : Boolean){
            Toast.makeText(activity, "Signup complete", Toast.LENGTH_SHORT).show()
            val action = SignupFragmentDirections.actionGoToMain()
            Navigation.findNavController(view!!).navigate(action)
        }
    }

    private val errorObserver = Observer<String> { error ->
        Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
    }

    private fun onSignup(v: View){
        val username = binding.signupUsername.text.toString()
        val password = binding.signupPassword.text.toString()
        val info = binding.otherInfo.text.toString()

        if(username.isNullOrEmpty() || password.isNullOrEmpty() || info.isNullOrEmpty()){
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.signup(username, password, info)
        }
    }

    private fun onGotoLogin(v: View) {
        LoginState.logout()
        val action = SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }
}
