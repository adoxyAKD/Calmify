package com.example.calmify.WEBVIEW_PAGE

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(activity: webviewActivity, private val tabCount:Int): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
       return tabCount
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0-> HtmlFragment()
           1-> AndroidFragment()
           else-> HtmlFragment()
       }
    }
}