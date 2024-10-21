package com.unitedtractors.elomate.ui.mentoring

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MentoringViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is mentoring Fragment"
    }
    val text: LiveData<String> = _text
}