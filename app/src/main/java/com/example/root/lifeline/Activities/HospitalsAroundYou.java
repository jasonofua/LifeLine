package com.example.root.lifeline.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.lifeline.NetworkUtils.APIClient;
import com.example.root.lifeline.NetworkUtils.ApiInterface;
import com.example.root.lifeline.POJO.PlacesPOJO;
import com.example.root.lifeline.POJO.ResultDistanceMatrix;
import com.example.root.lifeline.R;
import com.example.root.lifeline.adapters.RecyclerViewAdapter;
import com.example.root.lifeline.models.StoreModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HospitalsAroundYou extends AppCompatActivity {


    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    List<StoreModel> storeModels;
    ApiInterface apiService;
    List<PlacesPOJO.CustomA> results;
    boolean connected;

    String latLngString;
    LatLng latLng;
    RecyclerView hospitalsrRv;
    AlertDialog dialog;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals_around_you);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new SpotsDialog(HospitalsAroundYou.this, R.style.Custom);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            else {
                fetchLocation();
            }
        } else {
            fetchLocation();
        }


        apiService = APIClient.getClient().create(ApiInterface.class);

        hospitalsrRv = (RecyclerView)findViewById(R.id.rv_Hospitals);
        empty = (TextView)findViewById(R.id.empty);

        hospitalsrRv.setNestedScrollingEnabled(false);
        hospitalsrRv.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        hospitalsrRv.setLayoutManager(layoutManager);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchList("hospital","hospital");
            }
        });

    }

    private void fetchList(String hospital,String hospitalTitle) {
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            dialog.show();
            Call<PlacesPOJO.Root> call = apiService.doPlaces(hospital, latLngString, hospitalTitle, true, "distance", APIClient.GOOGLE_PLACE_API_KEY);
            call.enqueue(new Callback<PlacesPOJO.Root>() {
                @Override
                public void onResponse(Call<PlacesPOJO.Root> call, Response<PlacesPOJO.Root> response) {
                    PlacesPOJO.Root root = response.body();


                    if (response.isSuccessful()) {

                        if (root.status.equals("OK")) {
                            hospitalsrRv.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);

                            results = root.customA;
                            storeModels = new ArrayList<>();
                            for (int i = 0; i < results.size(); i++) {

                                if (i == 20)
                                    break;
                                PlacesPOJO.CustomA info = results.get(i);


                                fetchDistance(info);

                            }



                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't get hospitals near you, Kindly check your network connection and reload ", Toast.LENGTH_SHORT).show();
                        }

                    } else if (response.code() != 200) {
                        Toast.makeText(getApplicationContext(), "Error " + response.code() + " found.", Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<PlacesPOJO.Root> call, Throwable t) {
                    // Log error here since request failed
                    call.cancel();
                }
            });


        }
        else {
            connected = false;
            final View customDialog = HospitalsAroundYou.this.getLayoutInflater().inflate(R.layout.custom_dialog_select, null);

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HospitalsAroundYou.this, R.style.MyDialogTheme);
            dialogBuilder.setView(customDialog);

            dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    return;
                }
            });

            dialogBuilder.setCancelable(false);
            AlertDialog addCategoryDialog = dialogBuilder.create();
            addCategoryDialog.setTitle("NetWork Connectivity");
            addCategoryDialog.show();


        }


    }
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                } else {
                    fetchLocation();
                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HospitalsAroundYou.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void fetchLocation() {

        SmartLocation.with(this).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        latLngString = location.getLatitude() + "," + location.getLongitude();
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());


                    }
                });
    }

    private void fetchDistance(final PlacesPOJO.CustomA info) {

        Call<ResultDistanceMatrix> call = apiService.getDistance(APIClient.GOOGLE_PLACE_API_KEY, latLngString, info.geometry.locationA.lat + "," + info.geometry.locationA.lng);
        call.enqueue(new Callback<ResultDistanceMatrix>() {
            @Override
            public void onResponse(Call<ResultDistanceMatrix> call, Response<ResultDistanceMatrix> response) {

                ResultDistanceMatrix resultDistance = response.body();
                if ("OK".equalsIgnoreCase(resultDistance.status)) {

                    ResultDistanceMatrix.InfoDistanceMatrix infoDistanceMatrix = resultDistance.rows.get(0);
                    ResultDistanceMatrix.InfoDistanceMatrix.DistanceElement distanceElement = infoDistanceMatrix.elements.get(0);
                    if ("OK".equalsIgnoreCase(distanceElement.status)) {
                        ResultDistanceMatrix.InfoDistanceMatrix.ValueItem itemDuration = distanceElement.duration;
                        ResultDistanceMatrix.InfoDistanceMatrix.ValueItem itemDistance = distanceElement.distance;
                        String totalDistance = String.valueOf(itemDistance.text);
                        String totalDuration = String.valueOf(itemDuration.text);

                        storeModels.add(new StoreModel(info.name, info.vicinity, totalDistance, totalDuration));


                        if (storeModels.size() == 30 || storeModels.size() == results.size()) {
                            RecyclerViewAdapter adapterStores = new RecyclerViewAdapter(results, storeModels,HospitalsAroundYou.this);
                            hospitalsrRv.setAdapter(adapterStores);
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<ResultDistanceMatrix> call, Throwable t) {
                call.cancel();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HospitalsAroundYou.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
