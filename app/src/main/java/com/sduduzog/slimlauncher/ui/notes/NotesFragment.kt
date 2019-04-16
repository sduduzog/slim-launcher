package com.sduduzog.slimlauncher.ui.notes

import android.view.View
import android.widget.TextView
import com.sduduzog.slimlauncher.ui.BaseFragment

class NotesFragment : BaseFragment(){
    override fun getFragmentView(): View {
        return TextView(context)
    }
}