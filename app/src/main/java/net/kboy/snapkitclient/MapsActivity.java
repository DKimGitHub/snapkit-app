package net.kboy.snapkitclient;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private double myLat;
    private double myLang;
    //    private Button items;
//    private Button receive;
//    private LinearLayout lin;
    private Button items;
    private Button receive;
    //private LinearLayout lin;
    private DatabaseReference mDatabase;
    private DatabaseReference lati;
    private DatabaseReference longi;
    private int color = Color.TRANSPARENT;
    private Double myLati;
    private Double myLongi;
    private Timer time;
    private double[][] coords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference("tap-n-track");
        DatabaseReference loc = mDatabase.child("Items");
        DatabaseReference pho = loc.child("phone");
        lati = pho.child("lat");
        longi = pho.child("long");
        super.onCreate(savedInstanceState);
        coords = new double[5][2];
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int x = 0; x < coords.length; x++)
                    if(Math.abs(coords[x][0] - myLat) < .001 && Math.abs(coords[x][1] - myLang) < .001);


            }
        }, 0, 1000);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//        items = (Button)findViewById(R.id.items);
//        receive = (Button)findViewById(R.id.Receive);
//        lin = (LinearLayout)findViewById(R.id.lin);
//        final Drawable background = lin.getBackground();
        //lin = (LinearLayout)findViewById(R.id.lin);
        //final Drawable background = lin.getBackground();



//        receive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mMap.addMarker(new MarkerOptions().position(new LatLng(myLat + .00034, myLang + .0000943)).title("Friend1"));
//            }
//        });
//        items.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (background instanceof ColorDrawable) {
//                    color = ((ColorDrawable) background).getColor();
//                    if (color == Color.RED)
//                        lin.setBackgroundColor(Color.GREEN);
//                    if (color == Color.GREEN)
//                        lin.setBackgroundColor(Color.RED);
//                    Intent i = new Intent(MapsActivity.this, ItemsActivity.class);
//                    startActivity(i);
//                }
//
//            }
//        });
        pho.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> ne = (Map<String, Object>) dataSnapshot.getValue();
                Log.d("hi", "Value is: " + ne.get("lat"));
//                mMap.addMarker(new MarkerOptions().position(new LatLng((Double) ne.get("lat"), (Double) ne.get("long"))).title("Frank Tian"));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("bye", "Failed to read value.", error.toException());
            }
        });
//        lati.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                myLati = dataSnapshot.getValue(Double.class);
//                Log.d("latitude", "Value is: " + myLati);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("bye", "Failed to read value.", error.toException());
//            }
//        });
    }

    public void FlexTrophies(View v) {
        switch(v.getId()) {
            case R.id.ActivityMapButton:
                Intent myIntent = new Intent();
                myIntent.setClassName(this, "TrophyCaseActivity.java");
                // for ex: your package name can be "com.example"
                // your activity name will be "com.example.Contact_Developer"
                startActivity(myIntent);
                break;
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        final LatLng STC = new LatLng(43.470540, -80.543450);
        coords[0][0] = 43.470540;
        coords[0][1] = -80.543450;
        Marker stc = mMap.addMarker(new MarkerOptions()
                .position(STC)
                .title("Melbourne")
                .snippet("The Science Teaching Complex: currently home of 500+ sleep deprived students"));
        final LatLng Jacob = new LatLng(43.511610, -80.554050);
        coords[0][0] = 43.511610;
        coords[0][1] = -80.554050;
        Marker acob = mMap.addMarker(new MarkerOptions()
                .position(Jacob)
                .title("St. Jacob's Farmer's Market")
                .snippet("St. Jacob's Farmer's Market: A source for your locally grown "));
        final LatLng Googoo = new LatLng(43.515520, -80.512550);
        coords[0][0] = 43.515520;
        coords[0][1] = -80.512550;
        Marker gurgle = mMap.addMarker(new MarkerOptions()
                .position(Googoo)
                .title("Google")
                .snippet("Google Campus In Waterloo: A collection of people who are looking at your search history and laughing"));
        final LatLng Waterpark = new LatLng(43.458260, -80.519620);
        coords[0][0] = 43.458260;
        coords[0][1] = -80.519620;
        Marker Theloo = mMap.addMarker(new MarkerOptions()
                .position(Waterpark)
                .title("Waterloo Park")
                .snippet("Waterloo Park: Where old ladies go to feed birds and stuff. Great place to meet friendly doggos."));
        final LatLng Ice = new LatLng(43.472260, -80.555700);
        coords[0][0] = 43.472260;
        coords[0][1] = -80.555700;
        Marker Field = mMap.addMarker(new MarkerOptions()
                .position(Ice)
                .title("Columbia Icefield")
                .snippet("Columbia Icefield Athletic Center: A fitness center named after a placec that isn't nearby, and categories of sports that aren't played inside"));
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        // Logic to handle location object
                        myLat = location.getLatitude();
                        myLang = location.getLongitude();
                        longi.setValue(myLang);
                        lati.setValue(myLat);
//                        Log.d("gfghjhgfdfg", String.valueOf(myLat));
//                        LatLng current = new LatLng(myLat, myLang);
//                        mMap.addMarker(new MarkerOptions().position(current).title("Frank Tian"));
                    }
                });
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        createLocationRequest();
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        Log.d("location request called", "wprks");
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });
    }



// some more code


}



