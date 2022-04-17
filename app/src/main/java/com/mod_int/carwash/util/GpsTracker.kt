package com.mod_int.carwash.util

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task

class GpsTracker(private val application: Application) {

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(application)
    }

    private var cancellationTokenSource = CancellationTokenSource()

    @SuppressLint("MissingPermission")
    fun getLocation(): Result<Task<Location>> {
        return try {
            val currentLocationTask: Task<Location> =
                fusedLocationProviderClient.getCurrentLocation(
                    PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                )

            Result.Success(currentLocationTask)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun onCancel() {
        cancellationTokenSource.cancel()
    }

}