package com.example.todolistappfirstcopy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.todolistappfirstcopy.databinding.ActivityFirstBinding
import com.example.todolistappfirstcopy.signUpIn.SignUpInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        auth = Firebase.auth
        Handler().postDelayed({
            if (auth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            } else {
                startActivity(Intent(this, SignUpInActivity::class.java))
                this.finish()
            }
        },2000)
        setContentView(binding.root)
    }
}