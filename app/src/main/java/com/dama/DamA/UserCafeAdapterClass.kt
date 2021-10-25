package com.dama.DamA
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpager2.ImageFragment1
import com.example.viewpager2.ImageFragment2
import com.example.viewpager2.ImageFragment3


class UserCafeAdapterClass(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    companion object{
        private const val num_pages = 3 // 이 값에 따라 페이지가 생성됨. 중요
    }

    override fun getItemCount(): Int {
        return num_pages
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            return ImageFragment1()
        }
        else if(position == 1){
            return ImageFragment2()
        }
        else  {
            return ImageFragment3()
        }

    }
}