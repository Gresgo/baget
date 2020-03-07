package com.urtisi.baget.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.urtisi.baget.R
import com.urtisi.baget.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var binding: FragmentScheduleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        binding.viewModel = scheduleViewModel
        binding.executePendingBindings()

        return binding.root
    }
}