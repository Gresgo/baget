package com.urtisi.baget.ui.umk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UmkViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is umk!"
    }

    val text: LiveData<String> = _text
}