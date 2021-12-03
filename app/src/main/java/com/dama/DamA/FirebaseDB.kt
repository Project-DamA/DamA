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

    fun writeNewUser(userUid: String, name: String, email: String, phoneNumber:String) {
        database = Firebase.database.reference
        val user = User(userUid,name, email,phoneNumber)

        database.child("users").child(userUid).setValue(user)
    }

    fun writeNewOwner(ownerUid: String, name: String, email: String, phoneNumber:String) {
        database = Firebase.database.reference
        val owner = Owner(ownerUid,name, email,phoneNumber)

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


    fun writeRentalRequest(cafeOwnerUid:String,userUid:String) {
        database = Firebase.database.reference
        val rentalRequest = RentalRequest(userUid)

        database.child("request").child(cafeOwnerUid).child("rental").child(userUid).setValue(userUid)
    }

    fun writeReturnRequest(cafeOwnerUid:String,userUid:String) {
        database = Firebase.database.reference
        database.child("request").child(cafeOwnerUid).child("return").child(userUid).setValue(userUid)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeUserTumblerTime(user:User) {
        database = Firebase.database.reference
        val current = LocalDateTime.now()

        val newUser=User(user.uid,user.username,user.email,user.phoneNumber,current.toString())
        database.child("users").child(user.uid.toString()).setValue(newUser)
    }

    fun writeCafeRentalUsers(userUid: String){
        database = Firebase.database.reference
        val cafeOwnerUid=Firebase.auth.currentUser!!.uid
        database.child("cafe").child(cafeOwnerUid).child("rentalUsers").child(userUid).setValue(userUid)
    }

    fun removeUserTumblerTime(user:User) {
        database = Firebase.database.reference
        val newUser=User(user.uid,user.username,user.email,user.phoneNumber,null)
        database.child("users").child(user.uid.toString()).setValue(newUser)
    }

    fun removeCafeRentalUsers(userUid: String){
        database = Firebase.database.reference
        val cafeOwnerUid=Firebase.auth.currentUser!!.uid
        database.child("cafe").child(cafeOwnerUid).child("rentalUsers").child(userUid).removeValue()
    }

    fun addCafeRentalTumbler(){
        database = Firebase.database.reference
        var tumblerCount:Int=1
        val cafeOwnerUid=Firebase.auth.currentUser!!.uid
        val cafeTumblerRef=database.child("cafe").child(cafeOwnerUid).child("rentalTumbler")
        cafeTumblerRef.get().addOnSuccessListener {
            if(it.value!=0){
                tumblerCount=it.value.toString().toInt()+1
            }
            cafeTumblerRef.setValue(tumblerCount.toString())
        }

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

}