package com.urtisi.baget.ui.umk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.urtisi.baget.R
import kotlinx.android.synthetic.main.fragment_umk.view.*

class UmkFragment : Fragment() {

    private lateinit var umkViewModel: UmkViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        umkViewModel = ViewModelProviders.of(this).get(UmkViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_umk, container, false)
        val textView: TextView = root.text_umk

        umkViewModel.text.observe(this, Observer {
            textView.text = it
        })

        return root
    }
}