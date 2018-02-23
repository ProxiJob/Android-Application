package proxyjob.proxijob.Client

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import proxyjob.proxijob.Utils.APIManager
import proxyjob.proxijob.R
import proxyjob.proxijob.model.Company
import proxyjob.proxijob.model.Jobs
import proxyjob.proxijob.model.Localisation
import java.io.IOException
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by alexandre on 09/02/2018.
 */
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    override fun onInfoWindowClick(p0: Marker?) {
        var intent = Intent(activity, MapInformationDetails::class.java)
        intent.putExtra("objectID", Jobs!!.get(p0!!.id.toString().replace("m", "").toInt()).objectId)
                startActivity(intent)
    }

    fun newInstance(): MapFragment {
        return MapFragment()
    }
    var Jobs: ArrayList<Jobs>?= null
    var JobHash = HashMap<Jobs, String>()
    companion object {
        public const val LOCATION_PERMISSION_REQUEST_CODE = 1
        public const val REQUEST_CHECK_SETTINGS = 2
        public const val PLACE_PICKER_REQUEST = 3
    }
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    private lateinit var myContext: FragmentActivity

    override fun onAttach(context: Context?) {
        myContext = activity as FragmentActivity
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)

                    lastLocation = p0.lastLocation
                    //placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
                }
            }
            createLocationRequest()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.activity_maps, container, false)
        val fm = childFragmentManager
        var mapFragment: SupportMapFragment? = fm.findFragmentByTag("mapFragment") as SupportMapFragment?
        if (mapFragment == null) {
            mapFragment = SupportMapFragment()
            val ft = fm.beginTransaction()
            ft.add(R.id.mapFragment, mapFragment, "mapFragment")
            ft.commit()
            fm.executePendingTransactions()
        }
        mapFragment.getMapAsync(this)
        return rootView
    }

    protected fun createMarker(latitude: Double, longitude: Double) {

        val position = LatLng(latitude, longitude)
        var marker = MarkerOptions().position(position)
        map.addMarker(marker)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        //if (requestCode == MainActivity.REQUEST_CHECK_SETTINGS) {
        //  if (resultCode == Activity.RESULT_OK) {
        locationUpdateState = true
        startLocationUpdates()
        //  }
        //}
        /*if (requestCode == MainActivity.PLACE_PICKER_REQUEST) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                var addressText = place.name.toString()
                addressText = addressText.plus("\n" + place.address.toString())

                //placeMarkerOnMap(place.latLng)
            }
        }*/
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()

        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        APIManager.getShared().getJobs { b, error, arrayList ->
            this.Jobs = arrayList
            Log.i("DEBUG JOBS", "" + Jobs)

            for(i in Jobs!!.indices) {
                var localisation = Jobs!![i].fetchIfNeeded<Jobs>().company?.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.localisation
                if (localisation != null) {
                    Log.i("DEBUG LOC", "" + localisation)
                    createMarker(localisation?.latitude, localisation?.longitude)
            //createMarker(7.5, 43.7)
                }
            }
            map.uiSettings.isZoomControlsEnabled = true
            map.setOnMarkerClickListener(this)

            setUpMap()
        }

    }

    override fun onMarkerClick(p0: Marker?) = false

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        /*fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                //placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }*/



        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

            // Use default InfoWindow frame
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            // Defines the contents of the InfoWindow
            @TargetApi(Build.VERSION_CODES.O)
            @SuppressLint("SimpleDateFormat")
            override fun getInfoContents(arg0: Marker): View? {
                var v: View?= null
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                try {

                    // Getting view from the layout file info_window_layout

                    v = layoutInflater.inflate(R.layout.custom_infowindow, null)
                    var today = Calendar.getInstance().getTime();
                    var imageV = (v!!.findViewById<ImageView>(R.id.clientPic))

                    // (2) create a date "formatter" (the date format we want)
                    var formatter = SimpleDateFormat("dd/MM/YY")

                    // (3) create a new String using the date format we want
                    var folderName = formatter.format(Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).dateStart!!);
                    (v!!.findViewById<TextView>(R.id.date) as TextView).text = formatter.format(Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).dateStart!!) + "  au  " +
                            formatter.format(Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).dateEnd!!)
                    (v!!.findViewById<TextView>(R.id.job) as TextView).text = Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).job
                    (v!!.findViewById<TextView>(R.id.desc) as TextView).text = Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).description
                    (v!!.findViewById<TextView>(R.id.companyName) as TextView).text = Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).company!!.fetchIfNeeded<Company>()?.get("name") as String
                    var geo = Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).company!!.fetchIfNeeded<Company>()?.localisation?.fetchIfNeeded<Localisation>()?.localisation
                    (v!!.findViewById<TextView>(R.id.address) as TextView).text = getAddress(LatLng(geo!!.latitude, geo!!.longitude))
                    var logo = Jobs!!.get(arg0.id.toString().replace("m", "").toInt()).company!!.fetchIfNeeded<Company>()?.logo
                    Picasso.with(context).load(logo!!.url).into(imageV).run {
                        return v

                    }
                    sleep(100)
                } catch (ev: Exception) {
                    print(ev.message)
                }
                return v

            }
        })
        map.setOnInfoWindowClickListener { marker ->
            onInfoWindowClick(marker)
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)

        map.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(activity)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2

            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                    addressText = address!!.featureName + " " + address!!.thoroughfare
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        return addressText
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(activity)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(activity,
                            2)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(activity), 3)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }
}