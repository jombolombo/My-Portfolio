package com.example.newsapplication.adaptors

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapplication.fragments.HeadlineFragment
import com.example.newsapplication.fragments.MyNewsFragment
import com.example.newsapplication.fragments.VideosFragment

/**
 *Responsible for the fragments that enter the viewpager
 */
class TabsAdaptor(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val fragments: ArrayList<Fragment> = ArrayList<Fragment>()

    /**
     * @return number of fragments available
     */
    override fun getItemCount(): Int {
        return 3
    }

    /**
     * @param position
     * @return the fragment that the specified position
     */
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                fragments.add(0, HeadlineFragment())
                return fragments[0]
            }
            1 -> {
                fragments.add(1, MyNewsFragment())
                return fragments[1]
            }
            2 -> {
                fragments.add(2, VideosFragment())
                return fragments[2]
            }
            else -> return HeadlineFragment()
        }

    }
}