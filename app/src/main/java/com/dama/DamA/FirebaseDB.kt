package com.dama.DamA

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseDB {
    private lateinit var database: DatabaseReference

    fun writeNewUser(userId: String, name: String, email: String, phoneNumber:String) {
        database = Firebase.database.reference
        val user = User(name, email,phoneNumber)

        database.child("users").child(userId).setValue(user)
    }



}