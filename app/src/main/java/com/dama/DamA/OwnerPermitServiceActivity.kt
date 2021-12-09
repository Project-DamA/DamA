package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dama.DamA.databinding.ActivityOwnerPermitServiceBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class OwnerPermitServiceActivity : AppCompatActivity() {
    private lateinit var permitServiceAdapter: PermitServiceAdapter
    private lateinit var binding: ActivityOwnerPermitServiceBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var arrayList: ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerPermitServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RequestList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.RequestList.setHasFixedSize(true)

        arrayList = arrayListOf()
        getUserData("rental")

        binding.OwnerPermitServiceRentalBtn.setOnClickListener {
            getUserData("rental")
        }
        binding.OwnerPermitServiceReturnBtn.setOnClickListener {
            getUserData("return")
        }

    }


    private fun getUserData(requestType: String) {
        dbref = FirebaseDatabase.getInstance().getReference("request")
            .child(Firebase.auth.currentUser!!.uid).child(requestType)

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList = arrayListOf()
                if (snapshot.exists()) {
                    val userDBRef = FirebaseDatabase.getInstance().getReference("users").get()
                    userDBRef.addOnSuccessListener {
                        for (userSnapshot in snapshot.children) {
                            Log.d("userSnapshot", userSnapshot.toString())
                            val user = it.child(userSnapshot.value.toString()).getValue<User>()
                            Log.d("User", user.toString())
                            arrayList.add(user!!)


                        }
                        Log.d("arrayList", arrayList.toString())
                        permitServiceAdapter = PermitServiceAdapter(arrayList, requestType)
                        binding.RequestList.adapter = permitServiceAdapter
                        if (arrayList.isEmpty()) {
                            binding.OwnerPermitServiceNotUsersTv.visibility = View.VISIBLE
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}
