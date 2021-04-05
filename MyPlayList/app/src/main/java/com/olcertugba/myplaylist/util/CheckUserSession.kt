package com.olcertugba.myplaylist.util

import android.app.Application
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.olcertugba.myplaylist.activity.PlayListHomeActivity

class CheckUserSession: Application() {
   private lateinit var auth:FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        auth= FirebaseAuth.getInstance()
        val firebaseUser:FirebaseUser?= auth.currentUser

        if(firebaseUser!=null){
            val intent=Intent(this,PlayListHomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
    }
}