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
import com.devtides.coroutinesroom.databinding.FragmentLoginBinding
import com.devtides.coroutinesroom.model.LoginState
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

        loginCompleteObserver.view = view
        viewModel.loginComplete.observe(viewLifecycleOwner, loginCompleteObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)

        viewModel.loadDatabase(AppDatabase(requireContext()).userDao())
    }

    private val loginCompleteObserver = object : Observer<Boolean> {
        var view : View? = null

        override fun onChanged(isComplete : Boolean){
            Toast.makeText(activity, "Signup complete", Toast.LENGTH_SHORT).show()
            val action = LoginFragmentDirections.actionGoToMain()
            Navigation.findNavController(view!!).navigate(action)
        }
    }

    private val errorObserver = Observer<String> { error ->
        Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
    }


    private fun onLogin(v: View) {
        val username = binding.loginUsername.text.toString()
        val password = binding.loginPassword.text.toString()

        if(username.isNullOrEmpty() || password.isNullOrEmpty()){
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(username, password)
        }
    }

    private fun onGotoSignup(v: View){
        LoginState.logout()
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }
}
