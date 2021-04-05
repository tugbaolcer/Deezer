package com.olcertugba.myplaylist.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.olcertugba.myplaylist.MainActivity
import com.olcertugba.myplaylist.R

import kotlinx.android.synthetic.main.layout_login.*


class LoginActivity : AppCompatActivity() {

    companion object {
        var TAG = LoginActivity::class.java.simpleName

    }

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            signIn()
        }

        txtSingUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        btnLogin.visibility = View.INVISIBLE
        val email = editTextEmail!!.text.toString().trim()
        val password = editTextPassword!!.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val name = user?.email
                    val intent = Intent(this@LoginActivity, PlayListHomeActivity::class.java)
                    intent.putExtra("EXTRA_NAME", name)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    btnLogin.visibility = View.VISIBLE
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }
}