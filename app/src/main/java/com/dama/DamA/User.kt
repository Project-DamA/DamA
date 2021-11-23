package com.dama.DamA

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val username: String? = null, val email: String? = null, val phoneNumber:String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}