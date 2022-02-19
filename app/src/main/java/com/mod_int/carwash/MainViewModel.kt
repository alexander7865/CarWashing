package com.mod_int.carwash

import android.content.ClipData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val selected = MutableLiveData<ClipData.Item>()

    fun select(item: ClipData.Item) {
        selected.value = item
    }
}