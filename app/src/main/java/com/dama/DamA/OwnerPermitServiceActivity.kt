package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dama.DamA.databinding.ActivityOwnerPermitServiceBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class OwnerPermitServiceActivity : AppCompatActivity(){
    private lateinit var recyclerviewAdapter: RecyclerviewAdapter
    private lateinit var binding : ActivityOwnerPermitServiceBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var permitServiceRecyclerView : RecyclerView
    private lateinit var arrayList : ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerPermitServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RequestList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.RequestList.setHasFixedSize(true)

//        permitServiceRecyclerView = binding.RequestList
//        permitServiceRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//        permitServiceRecyclerView.setHasFixedSize(true)

        arrayList = arrayListOf()
        getUserData()
//        Log.d("arrayList1",arrayList.toString())


    }


    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("users")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue<User>()
                        arrayList.add(user!!)
                    }
                    recyclerviewAdapter=RecyclerviewAdapter(arrayList)
                    binding.RequestList.adapter = recyclerviewAdapter

                }
                else{
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}
