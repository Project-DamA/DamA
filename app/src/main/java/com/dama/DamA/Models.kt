package com.dama.DamA

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val uid:String?=null, val username: String? = null, val email: String? = null, val phoneNumber:String? = null,val rentalTime:String?=null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class Owner(val uid:String?=null, val ownername: String? = null, val email: String? = null, val phoneNumber:String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class Cafe(val ownerUid:String?=null,val cafeName: String? = null, val cafeSubName: String? = null, val location:String? = null,
                val call:String? = null, val runtime:String? = null, val facility:String? = null,
                val totalTumbler:String? = null, val rentalTumbler:String?=null, val rentalUsers:List<String>?=null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class RentalRequest(val userUid: String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class ReturnRequest(val userUid: String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class RentalUsers(val userId: String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

