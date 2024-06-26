package com.nikita_zayanchkovskij.newsapp.presentation.adapters.view_pager2_adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPager2Adapter(fragmentActivity: FragmentActivity,
    private val fragmentList: List<Fragment>): FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return fragmentList.size
    }


    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}