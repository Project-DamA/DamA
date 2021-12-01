package com.dama.DamA

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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

        database.child("owners").child(ownerId).setValue(owner)
    }

    fun writeCafeSetting(ownerUid:String,cafeName: String, cafeSubName: String, location: String, call:String, runtime:String, facility:String, rentalTumbler:String) {
        database = Firebase.database.reference
        val cafe = Cafe(cafeName, cafeSubName,location,call,runtime,facility,rentalTumbler)

        database.child("cafe").child(ownerUid).setValue(cafe)
    }

    //rentalRequest 승인 이후 cafeDB에 rentalUsers추가
    fun writeCafeRentalUsers(userId: String) {
        database = Firebase.database.reference
        val rentalUsers = RentalUsers(userId)

        database.child("cafe").child("1pRZNEauP4d0QejN6tQ85YruGQA3").child("rentalUsers").setValue(rentalUsers)
    }

    fun userOrOwner(uid:String):String{
        database = Firebase.database.reference
        val query=database.child("users")
        var result:String=""
        query.equalTo(uid).get().addOnCompleteListener{
            if(it.result!!.getValue()!=null) {
                val count=it.result!!.getChildrenCount().toInt()
                if(count==1){
                    result= "User"
                }
                else{
                    result="Owner"
                }
            }
        }

        return result
    }


    fun writeRentalRequest(userId:String) {
        database = Firebase.database.reference
        val rentalRequest = RentalRequest(userId)

        database.child("request").child("rental").child(userId).setValue(rentalRequest)
    }

    fun writeReturnRequest(userId:String) {
        database = Firebase.database.reference
        val returnRequest = ReturnRequest(userId)

        database.child("request").child("return").child(userId).setValue(returnRequest)
    }

    fun writeUserTumblerTime(time:String) {
        database = Firebase.database.reference
        val rentalTumblerTime = RentalTumbler(time)

        database.child("users").child("MbQVZ3yo3ENgvw2dn8TKSBMaFYw2").child("rentaltime").setValue(rentalTumblerTime)
    }

}