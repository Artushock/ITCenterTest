package com.artushock.itcentertest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.artushock.itcentertest.R
import com.artushock.itcentertest.databinding.FragmentDetailsBinding
import com.artushock.itcentertest.ui.models.Weather
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var map: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var weather: Weather

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weather = args.weather
        setWeatherInfo()

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    private fun setWeatherInfo() {
        val temp = if (weather.temp > 0.0) {
            "+${weather.temp}"
        } else if (weather.temp != 0.0) {
            "-${weather.temp}"
        } else {
            weather.temp.toString()
        }

        binding.text.text = getString(
            R.string.details_fragment_info,
            weather.place,
            temp,
            weather.humidity.toString(),
            weather.pressure.toString()
        )

        Glide.with(requireContext())
            .load("https:${weather.icon}")
            .centerCrop()
            .into(binding.image)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(m: GoogleMap) {
        this.map = m

        map.uiSettings.apply {
            isMapToolbarEnabled = false
            isCompassEnabled = false
            isTiltGesturesEnabled = false
            isRotateGesturesEnabled = false
            isMyLocationButtonEnabled = false
        }

        val location = LatLng(weather.latitude, weather.longitude)
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(weather.place)
        )
        val zoom = 10F
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom))
    }

}