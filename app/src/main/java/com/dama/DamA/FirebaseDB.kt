package com.dama.DamA

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
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

    fun writeCafeSetting(ownerUid:String,cafeName: String, cafeSubName: String, location: String, call:String, runtime:String, facility:String, rentalTumbler:String) {
        database = Firebase.database.reference
        val cafe = Cafe(cafeName, cafeSubName,location,call,runtime,facility,rentalTumbler)

        database.child("cafe").child(ownerUid).setValue(cafe)
    }

//    fun userOrOwner():String{
//        database = Firebase.database.reference
//        if (database.child("users").equalTo(Firebase.auth.currentUser?.uid).get().result!=null){
//            return "User"
//        }
//        else{
//            return "Owner"
//        }
//
//    }//todo: 수정 필요, 현재 작동 불가

}