package com.example.todolistappfirstcopy.signUpIn.utils

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.example.todolistappfirstcopy.MainActivity
import com.example.todolistappfirstcopy.R
import com.example.todolistappfirstcopy.signUpIn.SignUpInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpInHelper(private val act: SignUpInActivity) {
    private val auth = Firebase.auth

    fun forgotPassword(email: String){
        auth.sendPasswordResetEmail(email)
        Toast.makeText(act, "Reset password email was sent", Toast.LENGTH_SHORT).show()
    }

    fun signUpWithEmailAndPassword(email: String, password: String, username: String?){
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener{
            sendEmailVerification(it.user!!)
            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            it.user!!.updateProfile(updates)
        }.addOnFailureListener{
            Toast.makeText(act, "Error signing up", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendEmailVerification(user: FirebaseUser){
        user.sendEmailVerification()
        Toast.makeText(act, "Email for verification your account was sent", Toast.LENGTH_SHORT).show()
    }

    fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(act.getString(R.string.default_web_client_id))
            .requestProfile()
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(act, gso)
    }

    fun signInWithEmail(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener{
            Toast.makeText(act, "Sign In Successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(act, "Can not sign in", Toast.LENGTH_SHORT).show()
        }
    }

    fun signInWithGoogle(launcher: ActivityResultLauncher<Intent>){
        val googleSignInClient = getSignInClient()
        launcher.launch(googleSignInClient.signInIntent)
    }

    fun firebaseSignInWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(act, "Welcome!", Toast.LENGTH_SHORT).show()
                act.startActivity(Intent(act, MainActivity::class.java))
                act.finish()
            }
            else{
                Toast.makeText(act, "Error signing in", Toast.LENGTH_SHORT).show()
            }
        }
    }

}