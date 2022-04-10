package com.mod_int.carwash.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.mod_int.carwash.BuildConfig
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityMapBinding
import com.mod_int.carwash.ext.hasPermission
import com.mod_int.carwash.ext.showToast
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapActivity : BaseActivity<ActivityMapBinding>(R.layout.activity_map) {

    private var clickPosition = MapPOIItem()

    private val mapViewEventListener = object : MapView.MapViewEventListener {
        override fun onMapViewInitialized(p0: MapView?) {

        }

        override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        }

        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
            p0?.let {
                p1?.let {
                    makePOIItem(it)
                }
            }
        }

        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

        }

        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        }
    }

    private val mapPOIItemEventListener = object : MapView.POIItemEventListener {
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
            p1?.let {
                Toast.makeText(this@MapActivity, it.itemName, Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
        }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mapview.setMapViewEventListener(mapViewEventListener)
        binding.mapview.setPOIItemEventListener(mapPOIItemEventListener)

        locationRequest()
    }

    private fun makePOIItem(mapPoint: MapPoint) {
        binding.mapview.removePOIItem(clickPosition)
        clickPosition.apply {
            itemName = "Click Point"
            this.mapPoint = mapPoint
        }
        binding.mapview.addPOIItem(clickPosition)
        binding.mapview.setMapCenterPoint(mapPoint, true)
    }

    private fun locationRequest() {
        val permissionApproved =
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionApproved) {
            binding.mapview.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        } else {
            val provideRationale = shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            if (provideRationale) {
                Toast.makeText(this@MapActivity, "퍼미션 허용 0 ", Toast.LENGTH_SHORT).show()
            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    /**
     * GPS 권한 결과에 대한 처리.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE) {

            when {

                /**
                 * GPS 권한 x
                 */
                grantResults.isEmpty() -> {
                    showToast(message = "권한이 없습니다.")
                }

                /**
                 * GPS 권한 o
                 */
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    showToast(message = "권한이 허용되었습니다.")

                }

                /**
                 * GPS 권한 시스템 실행.
                 */
                else -> {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts(
                        "package",
                        BuildConfig.APPLICATION_ID,
                        null
                    )
                    intent.data = uri
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
    }


    companion object {
        const val REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE = 34
    }
}