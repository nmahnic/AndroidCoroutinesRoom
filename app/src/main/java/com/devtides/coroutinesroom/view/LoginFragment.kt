package com.devtides.coroutinesroom.view


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.databinding.FragmentLoginBinding
import com.devtides.coroutinesroom.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        loginBtn.setOnClickListener { onLogin(it) }
        gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        viewModel.loginComplete.observe(viewLifecycleOwner, loginCompleteObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    private val loginCompleteObserver = Observer<Boolean> { isComplete ->

    }

    private val errorObserver = Observer<String> { error ->

    }


    private fun onLogin(v: View) {
        val action = LoginFragmentDirections.actionGoToMain()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onGotoSignup(v: View){
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }
}
