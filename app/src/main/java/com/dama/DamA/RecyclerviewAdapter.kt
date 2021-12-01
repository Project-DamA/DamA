package com.dama.DamA
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dama.DamA.databinding.FragmentPermitServiceBinding

class RecyclerviewAdapter(private val List : ArrayList<User>): RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:FragmentPermitServiceBinding = FragmentPermitServiceBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_permit_service,
//            parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(List[position])
//        val currentitem = List[position]
//
//        holder.email.text = currentitem.email
//        holder.name.text = currentitem.username
//        holder.phoneNumber.text = currentitem.phoneNumber

    }

    override fun getItemCount(): Int {
        return List.size
    }

//    override fun setData(data:ArrayList<User>){
//        List=data
//        notifyDataSetChanged()
//    }
    class ViewHolder(val binding: FragmentPermitServiceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user:User){
            binding.PermitServiceEmailTv.text=user.email
            binding.PermitServiceNameTv.text=user.username
            binding.PermitServicePhoneNumberTv.text=user.phoneNumber


        }


//        val email : TextView = itemView.findViewById(R.id.PermitService_email_tv)
//        val name : TextView = itemView.findViewById(R.id.PermitService_name_tv)
//        val phoneNumber : TextView = itemView.findViewById(R.id.PermitService_phoneNumber_tv)

    }

}