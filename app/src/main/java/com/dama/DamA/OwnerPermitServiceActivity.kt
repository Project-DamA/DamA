package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dama.DamA.databinding.ActivityOwnerPermitServiceBinding
import com.google.firebase.database.*

class OwnerPermitServiceActivity : AppCompatActivity(){
    private lateinit var binding : ActivityOwnerPermitServiceBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var permitServiceRecyclerview : RecyclerView
    private lateinit var ArrayList : ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerPermitServiceBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_owner_permit_service)

        permitServiceRecyclerview = binding.RequestList
        permitServiceRecyclerview.layoutManager = LinearLayoutManager(this)
        permitServiceRecyclerview.setHasFixedSize(true)

        ArrayList = arrayListOf()
        getUserData()
    }


    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("users")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(User::class.java)
                        ArrayList.add(user!!)

                    }

                    permitServiceRecyclerview.adapter = RecyclerviewAdapter(ArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}