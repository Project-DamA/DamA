package com.dama.DamA
import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dama.DamA.databinding.FragmentCafeImageBinding

class ImageListAdapter(private val List : ArrayList<Uri>,private var activity:Activity): RecyclerView.Adapter<ImageListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:FragmentCafeImageBinding = FragmentCafeImageBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,activity)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }

    class ViewHolder(val binding: FragmentCafeImageBinding, private val activity:Activity) : RecyclerView.ViewHolder(binding.root){

        fun bind(uri:Uri){
            Glide.with(activity)
                .load(uri)
                .into(binding.CafeImageImageIv)
        }


    }

}