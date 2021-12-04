package com.dama.DamA

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.dama.DamA.databinding.FragmentPermitServiceBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PermitServiceAdapter(private val List: ArrayList<User>, val requestType: String) :
    RecyclerView.Adapter<PermitServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentPermitServiceBinding = FragmentPermitServiceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(List[position], requestType)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    class ViewHolder(val binding: FragmentPermitServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(user: User, requestType: String) {
            if (requestType == "rental") {
                binding.PermitServiceRequestTypeTv.text = "대여"
            } else {
                binding.PermitServiceRequestTypeTv.text = "반납"
            }
            binding.PermitServiceEmailTv.text = user.email
            binding.PermitServiceNameTv.text = user.username
            binding.PermitServicePhoneNumberTv.text = user.phoneNumber

            binding.PermitServicePermitBtn.setOnClickListener {
                if (requestType == "rental") {
                    FirebaseDatabase.getInstance().getReference("users").child(user.uid.toString())
                        .get().addOnSuccessListener {
                            val userRef: User = it.getValue<User>()!!
                            FirebaseDB().writeUserRental(userRef)
                        }
                } else {
                    FirebaseDatabase.getInstance().getReference("users").child(user.uid.toString())
                        .get().addOnSuccessListener {
                            val userRef: User = it.getValue<User>()!!
                            FirebaseDB().removeUserRental(userRef)
                        }
                }
                val dbRequestRef =
                    FirebaseDatabase.getInstance().getReference("request")
                        .child(Firebase.auth.currentUser!!.uid).child(requestType)
                        .child(user.uid.toString())
                dbRequestRef.removeValue()

            }
        }

    }

}