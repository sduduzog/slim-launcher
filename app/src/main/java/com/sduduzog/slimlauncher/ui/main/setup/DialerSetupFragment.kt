package com.sduduzog.slimlauncher.ui.main.setup


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.dialer_setup_fragment.*


class DialerSetupFragment : PagerHelperFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialer_setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup_dialer_switch.setOnCheckedChangeListener { _, b ->
            if (b) {
                setup_dialer_text.text = getString(R.string.setup_dialer_note2)
            } else {
                setup_dialer_text.text = getString(R.string.setup_note_dialer)
            }

            val settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)

            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_app_dialer), b)
            }
        }

        setup_button_next.setOnClickListener {
            listener?.onPage(4) // Move to next section
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = DialerSetupFragment()
    }
}
