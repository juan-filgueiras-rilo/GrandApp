package com.udc.grandapp.geolocalizacion

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest


class GPSTracker : Service(), LocationListener {

    // Flag for GPS status
    var isGPSEnabled = false

    // Flag for network status
    var isNetworkEnabled = false
    var location : Location? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    // The minimum distance to change Updates in meters
    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

    // The minimum time between updates in milliseconds
    private val MIN_TIME_BW_UPDATES: Long = 60000 // 1 minute

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null

    //constructor
    fun GPSTracker() {}

    // Método que obtiene la localización del usuario
    fun obtenerLocalizacionUsuario(context: Context) {
        try {
            locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager?
            if (locationManager != null) {
                // Getting GPS status
                isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
                // Getting network status
                isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (isGPSEnabled || isNetworkEnabled) {
                    //Intentamos recuperar los datos mediante el GPS
                    if (isGPSEnabled) {
                        if (location == null) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                Log.d("ERROR", "No se ha permitido acceder a la ubicación")
                                return
                            }
                            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                            val UPDATE_INTERVAL: Long = 15000 /* 15 secs */
                            val FASTEST_INTERVAL: Long = 5000 /* 5 secs */
                            val locationRequest = LocationRequest()
                            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                            locationRequest.interval = UPDATE_INTERVAL
                            locationRequest.fastestInterval = FASTEST_INTERVAL
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                val mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
                                val providers = mLocationManager.getProviders(true)
                                var bestLocation: Location? = null
                                for (provider in providers) {
                                    val l = mLocationManager.getLastKnownLocation(provider!!)
                                            ?: continue
                                    if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                                        // Found best last known location: %s", l);
                                        bestLocation = l
                                    }
                                }
                                location = bestLocation
                            }
                        }
                    }
                    // Si no hemos podido recuperar antes los datos, lo intentamos con las coordenadas que nos da la conexión de internet
                    if (location == null && isNetworkEnabled) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                        }
                        location = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) //Esto funciona bien
                    }
                }
            } else { //Si no lo tiene habilitado debemos permitir al usuario que lo hailite
                startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
            }
            if (location != null) {
                latitude = location!!.latitude
                longitude = location!!.longitude
            }
        } catch (e: Exception) {
            Log.d("TEST GPSTracker", "obtenerLocalizacionUsuario() " + e.message)
        }
    }

    // Stop using GPS listener. Calling this function will stop using GPS in your app
    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    // Function to get latitude
    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.latitude
        }
        // return latitude
        return latitude
    }

    //Function to get longitude
    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }
        // return longitude
        return longitude
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //Do nothing
    }

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }
}