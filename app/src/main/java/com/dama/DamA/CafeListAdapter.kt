package com.dama.DamA
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dama.DamA.databinding.FragmentCafeListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CafeListAdapter(val activity: UserMainActivity,val List : ArrayList<Cafe>): RecyclerView.Adapter<CafeListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:FragmentCafeListBinding = FragmentCafeListBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,activity)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }

    class ViewHolder(val binding: FragmentCafeListBinding,val activity: UserMainActivity) : RecyclerView.ViewHolder(binding.root){

        fun bind(cafe:Cafe){
            binding.CafeListCafeNameTv.text=cafe.cafeName

            binding.CafeListCafeCardCv.setOnClickListener {
                activity.gotoDetail(cafe.ownerUid.toString())

            }
        }


    }

}