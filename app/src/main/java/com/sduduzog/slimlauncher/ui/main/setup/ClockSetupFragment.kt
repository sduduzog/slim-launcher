package com.sduduzog.slimlauncher.ui.main.setup


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.clock_setup_fragment.*


class ClockSetupFragment : PagerHelperFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.clock_setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val settings = activity!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        clock_setup_switch.setOnCheckedChangeListener { _, b ->
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_clock_type), b)
            }
            if (b) {
                clock_setup_image.setImageResource(R.drawable.clock_zoomed2)
            } else {
                clock_setup_image.setImageResource(R.drawable.clock_zoomed)
            }
        }

        clock_setup_button.setOnClickListener {
            listener?.onPage(3) // Move to next section
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ClockSetupFragment()
    }
}
