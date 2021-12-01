package com.dama.DamA
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewAdapter(private val List : ArrayList<User>): RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_permit_service,
            parent,false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentitem = List[position]

        holder.email.text = currentitem.email
        holder.name.text = currentitem.username
        holder.phoneNumber.text = currentitem.phoneNumber

    }

    override fun getItemCount(): Int {

        return List.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val email : TextView = itemView.findViewById(R.id.PermitService_email_tv)
        val name : TextView = itemView.findViewById(R.id.PermitService_name_tv)
        val phoneNumber : TextView = itemView.findViewById(R.id.PermitService_phoneNumber_tv)

    }

}