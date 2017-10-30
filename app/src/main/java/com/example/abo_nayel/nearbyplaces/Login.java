package com.example.abo_nayel.nearbyplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import android.Manifest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;

public class Login extends AppCompatActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Button signUp,login;
    EditText name , pass;
    Location mCurrentLocation;
    String mLastUpdateTime;
    private LocationManager locationManager;
    private String provider;
    GoogleApiClient mGoogleApiClient;
    Location location;
    //RequestingLocationUpdates mRequestingLocationUpdates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        int location_permession_statua = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    99);

        }

        Log.d("zamel", "onCreate: " + location_permession_statua);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {

            onLocationChanged(location);

        } else {

        }
        Toast.makeText(Login.this,location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();
        signUp= (Button)findViewById(R.id.signup);
        login = (Button)findViewById(R.id.signin);
        name= (EditText)findViewById(R.id.mail);
        pass= (EditText)findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mySP = getSharedPreferences("User_Data",0);
                if(mySP.getString(name.getText().toString(),null)==(null)){
                    Toast.makeText(Login.this,"user name is incorrect ..",Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(Login.this, mySP.getString(name.getText().toString(), null), Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();

                    User user = gson.fromJson(mySP.getString(name.getText().toString(),null),User.class);
                    Toast.makeText(Login.this,"user name : "+user.name, Toast.LENGTH_SHORT).show();
                    if(name.getText().toString().equals(user.getName())&&pass.getText().toString().equals(user.getPassword())){
                        Intent i = new Intent(Login.this,Search.class);
                        i.putExtra("lat", location.getLatitude());
                        i.putExtra("lng",location.getLongitude());
                        startActivity(i);
                    }
//                    String thisUser = gson.toJson(mySP.getString(name.getText().toString(), null));
//
//                    JsonObject jsonObject = new JsonObject();
//                    JsonArray jsonArray = jsonObject.getAsJsonArray(name.getText().toString());
//                    //user = new Gson(jsonArray.toString(), User[].class);
//                    //Toast.makeText(Login.this, user[0].getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,SignUp.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("k", "onRequestPermissionsResult: " + "yes");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            99);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    } LocationRequest mLocationRequest;
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            createLocationRequest();
            final PendingResult<Status> statusPendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

            if (mLastLocation != null) {
                Log.d("zzz", "onConnected: "+ mLastLocation.getLatitude()+" m "+mLastLocation.getLongitude() );
                Toast.makeText(this, mLastLocation.getLatitude()+" m "+mLastLocation.getLongitude() , Toast.LENGTH_SHORT).show();
            }}
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("zzz", "locatiob changed: "+ location.getLatitude()+" m "+location.getLongitude() );
        Toast.makeText(Login.this,"the change"+ location.getLatitude()+" m "+location.getLongitude() , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

