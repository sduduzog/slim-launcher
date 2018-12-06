package com.sduduzog.slimlauncher.ui.main


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.dialer_fragment.*


class DialerFragment : Fragment(), View.OnClickListener {

    @Suppress("PropertyName")
    val TAG: String = "DialerFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editText2.showSoftInputOnFocus = false
        setupDialer()
        chevron.setOnClickListener {
            val index = editText2.selectionStart
            if (index != 0) {
                editText2.text.delete(editText2.selectionStart - 1, editText2.selectionEnd)
            }
            if (index == 1) chevron.visibility = View.INVISIBLE
        }
        chevron.setOnLongClickListener {
            editText2.text.clear()
            chevron.visibility = View.INVISIBLE
            true
        }

        fab_dialer.setOnClickListener {
            val number = editText2.text.toString()
            if (number.isEmpty()) return@setOnClickListener
            try {
                val i = Intent(Intent.ACTION_CALL)
                i.data = Uri.parse("tel:${Uri.encode(number)}")
                startActivity(i)
            } catch (e: Exception) {
                Log.e(TAG, "$e")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        editText2.text.clear()
    }

    private fun setupDialer() {
        dial_one.setOnClickListener(this)
        dial_two.setOnClickListener(this)
        dial_three.setOnClickListener(this)
        dial_four.setOnClickListener(this)
        dial_five.setOnClickListener(this)
        dial_six.setOnClickListener(this)
        dial_seven.setOnClickListener(this)
        dial_eight.setOnClickListener(this)
        dial_nine.setOnClickListener(this)
        dial_star.setOnClickListener(this)
        dial_zero.setOnClickListener(this)
        dial_hash.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val value = view.tag as String

        val index = editText2.selectionEnd

        chevron.visibility = View.VISIBLE

        editText2.text.insert(index, value)
    }
}
