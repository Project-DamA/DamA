package com.dama.DamA

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class FirebaseDB {
    private lateinit var database: DatabaseReference

    fun writeNewUser(userUid: String, name: String, email: String, phoneNumber:String) {
        database = Firebase.database.reference
        val user = User(name, email,phoneNumber)

        database.child("users").child(userUid).setValue(user)
    }

    fun writeNewOwner(ownerUid: String, name: String, email: String, phoneNumber:String) {
        database = Firebase.database.reference
        val owner = Owner(name, email,phoneNumber)

        database.child("owners").child(ownerUid).setValue(owner)
    }

    fun writeCafeSetting(ownerUid:String,cafeName: String, cafeSubName: String, location: String, call:String, runtime:String, facility:String, rentalTumbler:String) {
        database = Firebase.database.reference
        val cafe = Cafe(cafeName, cafeSubName,location,call,runtime,facility,rentalTumbler)

        database.child("cafe").child(ownerUid).setValue(cafe)
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

        database.child("request").child("Uoro2PfTmdUD41x7lVr0Ba0Ocp93").child("rental").child(userId).setValue(rentalRequest)
    }

    fun writeReturnRequest(userId:String) {
        database = Firebase.database.reference
        val returnRequest = ReturnRequest(userId)

        database.child("request").child("return").child(userId).setValue(returnRequest)
    }

    fun writeUserTumblerTime(user:User) {
        database = Firebase.database.reference
        val nowTime = System.currentTimeMillis()
        val date= Date(nowTime).toString()
        val newUser=User(user.uid,user.username,user.email,user.phoneNumber,date)
        database.child("users").child(user.uid.toString()).setValue(newUser)
    }

    fun writeCafeRentalUsers(userUid:String){
        database = Firebase.database.reference
        database.child("cafe").child("rentalUsers").child(userUid).setValue(userUid)
    }

}