package com.dama.DamA

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    fun writeNewOwner(ownerId: String, name: String, email: String, phoneNumber:String) {
        database = Firebase.database.reference
        val owner = Owner(name, email,phoneNumber)

        database.child("owner").child(ownerId).setValue(owner)
    }

    fun writeCafeSetting(cafename: String, cafesubname: String, location: String, phone:String, time:String, facility:String, rentaltumbler:Int) {
        database = Firebase.database.reference
        val cafe = Cafe(cafename, cafesubname,location,phone,time,facility,rentaltumbler)

        database.child("cafe").child(cafename).setValue(cafe)
    }

    /*private fun writeCafeSetting(cafename: String, cafesubname: String, location: String, phone: String, time: String, facility: String, rentaltumbler: Int) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val key = database.child("cafe").push().key
        if (key == null) {
            Log.w(AppCompatActivity.TAG, "Couldn't get push key for posts")
            return
        }
        val cafe = Cafe(cafename, cafesubname, location, phone, time, facility,rentaltumbler)
        val postValues = cafe.cafename()
        val childUpdates = hashMapOf<String, Any>(
            "/cafename/$key" to postValues,
            "/cafe-name/$cafename/$key" to postValues
        )
        database.updateChildren(childUpdates)
    }
*/

}