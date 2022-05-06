package com.example.todolistappfirstcopy.signUpIn.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.todolistappfirstcopy.MainActivity
import com.example.todolistappfirstcopy.R
import com.example.todolistappfirstcopy.databinding.FragmentSignInBinding
import com.example.todolistappfirstcopy.signUpIn.SignUpInActivity
import com.example.todolistappfirstcopy.signUpIn.utils.SignUpInHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class SignInFragment(private val act : SignUpInActivity) : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var signUpInHelper: SignUpInHelper
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        signUpInHelper = SignUpInHelper(act)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try{
                val account = task.getResult(ApiException::class.java)
                if(account!=null){
                    signUpInHelper.firebaseSignInWithGoogle(account.idToken!!)
                }
            }catch(e: ApiException){
                Log.d("MyTag", "onCreateView: $e")
            }
        }

        binding.buttonSignIn.setOnClickListener{
            if(binding.editTextSignInEmail.text.isNotEmpty() && binding.editTextSignInPassword.text.isNotEmpty()){
                signUpInHelper.signInWithEmail(binding.editTextSignInEmail.text.toString(), binding.editTextSignInPassword.text.toString())
                act.startActivity(Intent(act, MainActivity::class.java))
            }
            else{
                Toast.makeText(act, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvForgotPassword.setOnClickListener{
            if(binding.editTextSignInEmail.text.isNotEmpty())
                signUpInHelper.forgotPassword(binding.editTextSignInEmail.text.toString())
            else
                Toast.makeText(act, "Please enter email", Toast.LENGTH_SHORT).show()
        }

        binding.buttonSignInWithGoggle.setOnClickListener{
            signUpInHelper.signInWithGoogle(launcher)
        }

        auth = FirebaseAuth.getInstance()
        return binding.root
    }
}