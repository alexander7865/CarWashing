package com.mod_int.carwash.ext

import android.view.Gravity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mod_int.carwash.R


fun AppCompatActivity.showToast(message: String) {
    val toastCenter = Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    )
    toastCenter.setGravity(
        Gravity.CENTER,
        Gravity.CENTER_HORIZONTAL,
        0
    )
    toastCenter.show()
}

fun Fragment.showToast(message: String) {
    val toastCenter = Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_SHORT
    )
    toastCenter.setGravity(
        Gravity.CENTER,
        Gravity.CENTER_HORIZONTAL,
        0
    )
    toastCenter.show()
}


fun Fragment.showSpinner(
    spinner: Spinner,
    resourceList: Array<String>
): Spinner {
    val brandAdapter = ArrayAdapter(
        requireContext(),
        R.layout.custom_find_spinner, resourceList
    )

    spinner.adapter = brandAdapter
    return spinner
}