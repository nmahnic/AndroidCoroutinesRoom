package com.devtides.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.databinding.FragmentSignupBinding
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

        viewModel.signupComplete.observe(viewLifecycleOwner, signupCompleteObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    private val signupCompleteObserver = Observer<Boolean> { isComplete ->

    }

    private val errorObserver = Observer<String> { error ->

    }

    private fun onSignup(v: View){
        val action = SignupFragmentDirections.actionGoToMain()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }
}
