package com.example.todolistappfirstcopy.signUpIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolistappfirstcopy.R
import com.example.todolistappfirstcopy.databinding.ActivitySignUpInBinding
import com.example.todolistappfirstcopy.signUpIn.fragments.SignInFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivitySignUpInBinding.inflate(layoutInflater)
        supportFragmentManager.beginTransaction().replace(R.id.signUpInFragmentContainer, SignInFragment(this)).commit()
        setContentView(binding.root)
    }
}