package com.sduduzog.slimlauncher.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sduduzog.slimlauncher.R

class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_framgent, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
