package com.example.easytravel.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.easytravel.Fragments.Buses1Fragment
import com.example.easytravel.Fragments.Buses4Fragment
import com.example.easytravel.Fragments.Buses6Fragment

class FragmentsAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Buses1Fragment()
            1 -> Buses4Fragment()
            2 -> Buses6Fragment()
            else -> Buses1Fragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "1:00 pm"
        }
        if (position == 1) {
            title = "4:00 pm"
        }
        if (position == 2) {
            title = "6:00 pm"
        }
        return title
    }
}