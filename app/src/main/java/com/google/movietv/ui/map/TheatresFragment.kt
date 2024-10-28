package com.google.movietv.ui.map

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.movietv.R
import com.google.movietv.databinding.FragmentTheatresBinding
import com.google.movietv.model.Cinema
import com.google.movietv.ui.checkPermission
import com.google.movietv.ui.getBitmapFromVector
import com.google.movietv.ui.main.MainViewModel
import kotlinx.coroutines.launch

class TheatresFragment : Fragment() {

    private var _binding: FragmentTheatresBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("____","onCreateView Map")
        _binding = FragmentTheatresBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private val mapReadyCallback = OnMapReadyCallback { googleMap ->
        this.map = googleMap
        observeCinemaList()
        initMap()
    }



    private fun setUpViews() = with(binding){
        requestMap()
    }
    private fun observeCinemaList() = with(binding) {
        viewModel.cinemaList.observe(viewLifecycleOwner) {
            onPlaceBranchPoints(it)
        }
    }

    private fun requestMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)
    }

    private fun initMap() {
        map?.apply {
            setMinZoomPreference(MIN_ZOOM)
            if (requireActivity().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = false
                val bishkek = LatLngBounds(
                    LatLng(42.7704821, 74.3240387),
                    LatLng(42.9350295, 74.7903572)
                )
                setLatLngBoundsForCameraTarget(bishkek)
                moveCamera(CameraUpdateFactory.newLatLng(LatLng(42.8768537, 74.5218215)))
            } else {
                requestLocationPermission()
            }
        }
    }


    private var requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if(it) {
            initMap()
        }
    }
    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }


    private fun onPlaceBranchPoints(points: List<Cinema>) {
        val googleMap = map ?: return

        points.forEach { branch ->
            placeMarker(branch)
        }
        if (points.isEmpty()) {
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(42.87502, 74.580939), MIN_ZOOM)
            )
            return
        }

    }

    private fun placeMarker(cinema: Cinema) {
        map?.addMarker(
            MarkerOptions()
                .position(cinema.latLng)
                .icon(
                    requireContext().getBitmapFromVector(
                        R.drawable.ic_location
                    )
                )
                .title(cinema.theatreName)
                .snippet(cinema.theatreName + " FilmX cinema list")
                .flat(true)
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        const val MIN_ZOOM = 10F
        const val CURRENT_LOCATION_ZOOM = 18F
    }

}