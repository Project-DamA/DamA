package com.dama.DamA

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.FragmentViewpagerBinding


class UserCardVIewPagerFragment(private val List: ArrayList<User>): Fragment() {
    private lateinit var mBinding: FragmentViewpagerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentViewpagerBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // RecyclerView.Adapter<ViewHolder>()
        mBinding.viewPager.adapter = UserCardRentalAdapter(List)
        // ViewPager의 Paging 방향은 Horizontal
        mBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


    }
}