package com.dama.DamA

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dama.DamA.databinding.FragmentUsercardRentalBinding

class UserCardRentalAdapter(private val List: ArrayList<User>) :
    RecyclerView.Adapter<UserCardRentalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentUsercardRentalBinding = FragmentUsercardRentalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }

    class ViewHolder(val binding: FragmentUsercardRentalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.UserCardRentalEmailTv.text = user.email
            binding.UserCardRentalNameTv.text = user.username
            binding.UserCardRentalPhoneNumberTv.text = user.phoneNumber
            binding.UserCardRentalRentalTimeTv.text = user.rentalTime

        }
    }


}

