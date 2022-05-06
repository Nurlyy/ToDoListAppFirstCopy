package com.example.todolistappfirstcopy.signUpIn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolistappfirstcopy.R
import com.example.todolistappfirstcopy.databinding.FragmentSignUpBinding
import com.example.todolistappfirstcopy.signUpIn.SignUpInActivity
import com.example.todolistappfirstcopy.signUpIn.utils.SignUpInHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment(val act: SignUpInActivity) : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var signUpInHelper: SignUpInHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        signUpInHelper = SignUpInHelper(act)

        binding.buttonSignUp.setOnClickListener{
            if(binding.editTextSignUpEmail.text.isNotEmpty() &&
                binding.editTextSignUpPassword.text.equals(
                    binding.editTextSignUpConfirmPassword.text)){
                signUpInHelper.signUpWithEmailAndPassword(binding.editTextSignUpEmail.text.toString(),
                binding.editTextSignUpPassword.text.toString(), binding.editTextSignUpUsername.text.toString())
            }
        }

        binding.tvAlreadySignedUp.setOnClickListener{
            act.supportFragmentManager.beginTransaction().replace(R.id.signUpInFragmentContainer, SignInFragment(act)).commit()
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}