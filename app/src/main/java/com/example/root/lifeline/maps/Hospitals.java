package com.example.root.lifeline.maps;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.root.lifeline.Constant.IntentExtras;
import com.example.root.lifeline.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class Hospitals extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMap hospitalLocation;
    double myLat;
    double myLng;
    LatLng myLatLng;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker mCurrLocationMarker2;
    double latitudeS;
    double longitudes;
    String hospitalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals2);

        latitudeS = getIntent().getDoubleExtra(IntentExtras.LATITUDE,0);
        longitudes = getIntent().getDoubleExtra(IntentExtras.LONGITUDE,0);
        hospitalName = getIntent().getStringExtra(IntentExtras.HOSPITAL);


        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d("onCreate", "Google Play Services not available. Ending Test case.");
            finish();
        }
        else {
            Log.d("onCreate", "Google Play Services available. Continuing.");
        }

        getCurrrentLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getCurrrentLocation() {
        SmartLocation.with(this).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {

                        mLastLocation = location;
                        if (mCurrLocationMarker != null) {
                            mCurrLocationMarker.remove();
                        }
                        mMap.clear();

                        myLat = location.getLatitude();
                        myLng =location.getLongitude();
                        myLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(myLatLng);
                        markerOptions.title("Am Here");

                        // Adding colour to the marker
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                        // Adding Marker to the Map
                        mCurrLocationMarker = mMap.addMarker(markerOptions);

                        //move map camera
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                        if (mCurrLocationMarker2 != null) {
                            mCurrLocationMarker2.remove();
                        }

                        MarkerOptions markerOptions2 = new MarkerOptions();
                        LatLng latLng = new LatLng(latitudeS, longitudes);
                        markerOptions2.title(hospitalName);
                        // Position of Marker on Map
                        markerOptions2.position(latLng);
                        // Adding Title to the Marker
                        // Adding Marker to the Camera.
                        mCurrLocationMarker2 = mMap.addMarker(markerOptions2);
                        // Adding colour to the marker
                        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                        // Creating CameraUpdate object for position
                        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(latLng);
                        // Creating CameraUpdate object for zoom
                        hospitalLocation.moveCamera(updatePosition);
                        hospitalLocation.animateCamera(CameraUpdateFactory.zoomTo(11));

                        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", myLat, myLng));

                        Log.d("onLocationChanged", "Exit");
                    }
                });
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        hospitalLocation = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        mMap.clear();


        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(latitudeS, longitudes);
        // Position of Marker on Map
        markerOptions.position(latLng);
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        // Adding colour to the marker
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(latLng);
        // Creating CameraUpdate object for zoom
        mMap.moveCamera(updatePosition);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


    }


}
