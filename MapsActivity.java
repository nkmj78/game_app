package edu.android.appgame;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "map_tag";
    private GoogleMap mMap;

    private Double latitude;
    private Double longitude;
    private List<String> lat = new ArrayList<>();
    private List<String> lon = new ArrayList<>();
    private List<String[]> facilityList = new ArrayList<>();
    private List<String> centerName = new ArrayList<>();
    private List<String> centerAddr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 아이티윌 위치 지정
        latitude = 37.4995367;
        longitude = 127.0293196;

        StrictMode.enableDefaults();

        getLatLonFromFile();
    }

    public void getLatLonFromFile() {
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            in = getAssets().open("facility_list.txt");
            reader = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(reader);

            String line = br.readLine();
            while (line != null) {
                String[] list = line.split(",");
                facilityList.add(list);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setLatLon();
    } // end getLatLonFromFile()

    public void setLatLon(){
        for(int i = 0; i < facilityList.size(); i++){
            centerName.add(facilityList.get(i)[1]);
            centerAddr.add(facilityList.get(i)[2]);
            lat.add(facilityList.get(i)[4]);
            lon.add(facilityList.get(i)[5]);
            Log.i(TAG,"이름: " + facilityList.get(i)[1] + "주소" + facilityList.get(i)[2]);
            Log.i(TAG, "lat: " +facilityList.get(i)[4] + "lon: " +facilityList.get(i)[5] );
        }

//        facilityList.get(i)[0]
    } // end setLatLon()


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

        LatLng myLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("내 위치")).showInfoWindow();

        for (int i = 0; i < lat.size(); i++) {
            LatLng centerLocation = new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lon.get(i)));
            mMap.addMarker(new MarkerOptions()
                    .position(centerLocation)
                    .title(centerName.get(i))
                    .snippet(centerAddr.get(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                    .showInfoWindow();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
    } // end onMapReady()
} // end class MapsActivity