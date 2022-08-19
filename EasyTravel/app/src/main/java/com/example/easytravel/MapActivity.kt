package com.example.easytravel

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.ViewModels.MapViewModel
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivityMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    lateinit var usersViewModel: UsersViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment : SupportMapFragment
    private lateinit var client: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include.imgback.visibility = View.VISIBLE
        binding.include.imgback.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(this, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
        })

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.idSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are getting the
                // location name from search view.
                val location: String = binding.idSearchView.getQuery().toString()

                // below line is to create a list of address
                // where we will store the list of all address.
                var addressList: List<Address>? = null

                // checking if the entered location is null or not.
                if (location != null || location == "") {
                    // on below line we are creating and initializing a geo coder.
                    val geocoder = Geocoder(this@MapActivity)
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    val address: Address = addressList!![0]

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    val latLng = LatLng(address.getLatitude(), address.getLongitude())

                    // on below line we are adding marker to that position.
                    mMap.addMarker(MarkerOptions().position(latLng).title(location).icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_flag)))

                    // below line is to animate camera to that position.
                    pointToPosition(latLng)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        mapViewModel.MyPost().observe(this, Observer {
            for (i  in it){
                val position = LatLng(i.latitude!!.toDouble(), i.longitude!!.toDouble())
                mMap.addMarker(MarkerOptions().position(position).title(i.title.toString()).icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_stop)))
            }

        })

        client = LocationServices.getFusedLocationProviderClient(this)
        binding.floatingActionButton.setOnClickListener{
            Dexter.withContext(applicationContext)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                        getmylocation()
                    }

                    override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {

                    }
                    override fun onPermissionRationaleShouldBeShown(
                        permissionRequest: PermissionRequest,
                        permissionToken: PermissionToken
                    ) {
                        permissionToken.continuePermissionRequest()
                    }
                }).check()
        }

    }

    fun getmylocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val task = client.lastLocation

        task.addOnSuccessListener { location ->
            mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
                val latLng = LatLng(location.latitude, location.longitude)
                val markerOptions = MarkerOptions().position(latLng).title("You are here...!!")
                googleMap.addMarker(markerOptions)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(33.75067402832623, 73.13643345527153)
        mMap.addMarker(MarkerOptions().position(sydney).title("Quaid-i-Azam University (Transport Point)").icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_stop)))
        pointToPosition(sydney)
    }

    private fun pointToPosition(position: LatLng) {
        //Build camera position
        val cameraPosition = CameraPosition.Builder()
            .target(position)
            .zoom(17f).build()
        // Add a marker
        //mMap.addMarker(MarkerOptions().position(position).title("G-9/2 Islamabad"))
        //Zoom in and animate the camera.
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}