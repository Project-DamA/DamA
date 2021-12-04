package com.dama.DamA

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FirebaseDB {
    private lateinit var database: DatabaseReference
    val ownerUid=Firebase.auth.currentUser!!.uid
    val userUid=Firebase.auth.currentUser!!.uid

    //currentUser=user
    fun writeNewUser(user:User) {
        database = Firebase.database.reference
        database.child("users").child(user.uid!!).setValue(user)
    }

    fun writeRequest(cafeOwnerUid:String,requestType:String) {
        database = Firebase.database.reference
        database.child("request").child(cafeOwnerUid).child(requestType).child(userUid).setValue(userUid)
    }





    //currentUser=owner
    fun writeNewOwner(owner:Owner) {
        database = Firebase.database.reference

        database.child("owners").child(ownerUid).setValue(owner)
    }

    fun writeCafeSetting(cafe:Cafe) {
        database = Firebase.database.reference
        database.child("cafe").child(cafe.ownerUid!!).setValue(cafe)
    }

    fun addCafeRentalTumbler(){
        database = Firebase.database.reference
        var tumblerCount:Int=1
        val cafeTumblerRef=database.child("cafe").child(ownerUid).child("rentalTumbler")
        cafeTumblerRef.get().addOnSuccessListener {
            if(it.value!=0){
                tumblerCount=it.value.toString().toInt()+1
            }
            cafeTumblerRef.setValue(tumblerCount.toString())
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeUserRental(user:User) {
        database = Firebase.database.reference
        val current = LocalDateTime.now()

        //유저에 빌린 시간과 카페 owner uid 추가
        val newUser=User(user.uid,user.username,user.email,user.phoneNumber,current.toString(),ownerUid)
        database.child("users").child(user.uid!!).setValue(newUser)
        //cafe에 유저 uid 추가
        database.child("cafe").child(ownerUid).child("rentalUsers").child(user.uid).setValue(user.uid)
        addCafeRentalTumbler()

    }

    fun subCafeRentalTumbler(){
        database = Firebase.database.reference
        var tumblerCount:Int=0
        val cafeOwnerUid=Firebase.auth.currentUser!!.uid
        val cafeTumblerRef=database.child("cafe").child(cafeOwnerUid).child("rentalTumbler")
        cafeTumblerRef.get().addOnSuccessListener {
            if(it.value!=0){
                tumblerCount=it.value.toString().toInt()-1
            }
            cafeTumblerRef.setValue(tumblerCount.toString())
        }
    }

    fun removeUserRental(user:User) {
        database = Firebase.database.reference

        //유저에 빌린 시간과 카페 owner uid 삭제
        val newUser=User(user.uid,user.username,user.email,user.phoneNumber,null,null)
        database.child("users").child(user.uid.toString()).setValue(newUser)
        //cafe에 유저 uid 삭제
        database.child("cafe").child(ownerUid).child("rentalUsers").child(user.uid!!).removeValue()
        subCafeRentalTumbler()
    }




}