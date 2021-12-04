package com.dama.DamA

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class User(val uid:String?=null, val username: String? = null, val email: String? = null, val phoneNumber:String? = null,val rentalTime:String?=null,val rentalCafe:String?=null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class Owner(val uid:String?=null, val ownername: String? = null, val email: String? = null, val phoneNumber:String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class Cafe(val ownerUid:String?=null,val cafeName: String? = null, val cafeSubName: String? = null, val location:String? = null,
                val call:String? = null, val runtime:String? = null, val facility:String? = null,
                val totalTumbler:String? = "0", val rentalTumbler:String?="0",val expiryTumbler:String?="0", val rentalUsers:Map<String,String>?=null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}



