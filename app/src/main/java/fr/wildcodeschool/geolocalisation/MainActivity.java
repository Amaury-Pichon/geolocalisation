package fr.wildcodeschool.geolocalisation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager mLocationManager = null;
    public static final int MY_PERMISSION_REQUEST_ACCES_FINE_LOCATION = 100;
    LocationListener locationListener;

    @SuppressLint("MissingPermission")
    private void initLocation(){
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

    }

    @Override
    protected void onStop() {
        super.onStop();

        mLocationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        initLocation();
    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //Authorisation pas acceptée

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

                //Autorisation refusée précédemment, prévenir l'utilisateur ici

            }
            else {
                //Autorisation jamais réclamée, on la demande à l'utilisateur

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_ACCES_FINE_LOCATION);
            }
        }
        else {
            //TODO : Permission already granted, we do stuff here

            initLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_ACCES_FINE_LOCATION : {

                if(grantResults.length >0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    initLocation();

                    //yay permission granted!

                }
                else {
                    //boo, permission not granted :-(
                }
                return;
            }

        }

    }
}
