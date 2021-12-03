package com.dama.DamA
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dama.DamA.databinding.FragmentCafeListBinding

class CafeListAdapter(private val activity: UserMainActivity,private val List : ArrayList<Cafe>): RecyclerView.Adapter<CafeListAdapter.ViewHolder>(){
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

    class ViewHolder(val binding: FragmentCafeListBinding,private val activity: UserMainActivity) : RecyclerView.ViewHolder(binding.root){

        fun bind(cafe:Cafe){
            binding.CafeListCafeNameTv.text=cafe.cafeName

            binding.CafeListCafeCardCv.setOnClickListener {
                activity.gotoDetail(cafe.ownerUid.toString())

            }
        }


    }

}