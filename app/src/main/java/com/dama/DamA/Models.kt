package com.dama.DamA

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val username: String? = null, val email: String? = null, val phoneNumber:String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class Owner(val ownername: String? = null, val email: String? = null, val phoneNumber:String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class Cafe(val cafeName: String? = null, val cafeSubName: String? = null, val location:String? = null,
                val call:String? = null, val runtime:String? = null, val facility:String? = null, val rentalTumbler:String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

data class RentalRequest(val userId: String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}