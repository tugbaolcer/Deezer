package com.olcertugba.myplaylist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.olcertugba.myplaylist.MainActivity
import com.olcertugba.myplaylist.R
import kotlinx.android.synthetic.main.layout_login.*
import kotlinx.android.synthetic.main.layout_signup.*

class SignUpActivity : AppCompatActivity() {
    companion object {
        var TAG = SignUpActivity::class.java.simpleName
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        db = Firebase.firestore

        btnSignUp.setOnClickListener {
            signUp()

        }

        txtSignIn.setOnClickListener {
            val intent=Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp() {
        val name =txtName.text.toString().trim()
        val email = txtEmail.text.toString().trim()
        val password = txtPassword.text.toString()

        if (name.isEmpty()) {
            txtName.error = " Please enter name"
            txtName.requestFocus()
            return
        }


        if (email.isEmpty()) {
            txtEmail.error = " Please enter email address"
            txtEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.error = "pleaese enter valid email address"
            txtEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            txtPassword.error = " Please enter password"
            txtPassword.requestFocus()
            return
        }
        btnSignUp.visibility = View.INVISIBLE
        db.collection("users").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "addOnSuccessListener ${document.data["username"]}")
                        if (document.data["username"] == name) {
                            Log.d(TAG, "addOnSuccessListener ${document.data["username"]}")
                            txtName.error = "Username already exist"
                            Toast.makeText(this, "Username already exist", Toast.LENGTH_SHORT)
                                    .show()
                            btnSignUp.visibility = View.VISIBLE
                            return@addOnSuccessListener
                        }
                    }
                    register(email, password, name)
                }
                .addOnFailureListener {e ->
                    btnSignUp.visibility = View.VISIBLE
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT ).show()
                }
    }

    private fun register(email: String, password: String, name: String){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        user?.updateProfile(profileUpdates)
                        val userdata = hashMapOf(
                                "name" to name,
                                "email" to email
                        )
                        db.collection("users")
                                .add(userdata)
                        val intent =
                                Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        btnSignUp.visibility = View.VISIBLE
                        Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
    }
}