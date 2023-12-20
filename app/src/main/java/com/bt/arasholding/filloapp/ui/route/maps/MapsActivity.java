package com.bt.arasholding.filloapp.ui.route.maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.AdresKoorModel;
import com.bt.arasholding.filloapp.data.model.HttpDataHandlerGeocoding;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRoute;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteActivity;
import com.bt.arasholding.filloapp.ui.route.address.ExpeditionRouteAddressActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity{
    ArrayList<String> rotaAdresleri = new ArrayList<>();
    ArrayList<LatLng> markerPoints;
    ArrayList<Integer> waylist = new ArrayList<Integer>();
    ArrayList<AdresKoorModel> optimizeRota = new ArrayList<>();
    protected GoogleMap map;
    String lat;
    String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        markerPoints = new ArrayList<LatLng>();
        rotaAdresleri = getIntent().getStringArrayListExtra("adresler"); // TODO: 17.02.2020 Adresler
        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");
//        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        fm.getMapAsync(this);

        new GetCoordinates().execute(rotaAdresleri);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapsActivity.this, ExpeditionRouteActivity.class);
        startActivity(intent);
        finish();
    }

    //    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        googleMap.setMyLocationEnabled(true);
//
//        if (markerPoints.size() >= 70) {
//            return;
//        }
//
//        for (LatLng item : markerPoints) {
//            LatLng loc = new LatLng(item.latitude, item.longitude);
//            googleMap.addMarker(new MarkerOptions().position(loc).title("locationName"));
//        }
//        // Creating MarkerOptions
////        MarkerOptions options = new MarkerOptions();
////
////        // Setting the position of the marker
////        options.position(point);
//
//        // Add new marker to the Google Map Android API V2
////        googleMap.addMarker(options);
//        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//
//            @Override
//            public void onMapLongClick(LatLng point) {
//                // Removes all the points from Google Map
//                googleMap.clear();
//
//                // Removes all the points in the ArrayList
//                markerPoints.clear();
//            }
//        });
//    }
    public void Onizleme() {

        StringBuilder uri = new StringBuilder(); //TODO BURAYA BAKILACAK
//        /41.09429023,29.08940748
        StringBuilder yeni = uri.append("https://www.google.com/maps/dir/");// Navigasyon için google mapse aktarılıyor
        yeni.append(lat + "," + lon+ "/");
        if (optimizeRota.size() <= 23) {
            for (int i = 0; i < optimizeRota.size(); i++) {
                AdresKoorModel akm = optimizeRota.get(i);
                yeni.append(akm.getPoint().latitude).append(",").append(akm.getPoint().longitude).append("/");
            }
        } else {
            for (int i = 0; i < 23; i++) {
                AdresKoorModel akm = optimizeRota.get(i);
                yeni.append(akm.getPoint().latitude).append(",").append(akm.getPoint().longitude).append("/");
            }

        }
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(yeni.toString()));
        startActivity(intent);
    }
    private class GetCoordinates extends AsyncTask<ArrayList<String>, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MapsActivity.this);
            dialog.setMessage("Koordinatlar bulunuyor.Lütfen bekleyiniz...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(ArrayList<String>... strings) {
            String response;
            double sonuc = rotaAdresleri.size() / 23.0;

            if (rotaAdresleri.size() <= 23) {
                for (int i = 0; i <= rotaAdresleri.size(); i++) {
                    try {
                        HttpDataHandlerGeocoding http = new HttpDataHandlerGeocoding();
                        String key = "&key=AIzaSyARopHLIjSXbO6yY9iX-KqwdvjfzzqjRjE";

                        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s", rotaAdresleri.get(i) + key);

                        response = http.getHttpData(url);

                        JSONObject jsonObject = new JSONObject(response);
                        String lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                        String lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();

                        markerPoints.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                    } catch (Exception e)
                    {

                    }
                }
            }
            int toplamKoordinat = 0;
            if (rotaAdresleri.size() <= 23) {
                toplamKoordinat = markerPoints.size();
//                return "Toplam " + toplamKoordinat + " adres bulundu";
            }
//            Toast.makeText(getApplicationContext(),"Toplam "+toplamKoordinat+" adres bulundu",Toast.LENGTH_LONG).show();

            return "başarılı";

        }
        @Override
        protected void onPostExecute(String s) {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            getDirectionsUrl();

        }
    }

    private void getDirectionsUrl() {

        // Waypoints
        String waypoints = "";

        String key = "key=AIzaSyARopHLIjSXbO6yY9iX-KqwdvjfzzqjRjE";
        waypoints = "waypoints=optimize:true|";

        if (rotaAdresleri.size() <= 23) {
            new GetDirectionsKucuk(this).execute();

        }
    }

    private class GetDirectionsKucuk extends AsyncTask<Void, Void, Void> {
        String waypoints = "waypoints=optimize:true|";

        String key = "key=AIzaSyARopHLIjSXbO6yY9iX-KqwdvjfzzqjRjE";

        ProgressDialog dialog;
        public MapsActivity activity;

        public GetDirectionsKucuk(MapsActivity a) {
            this.activity = a;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MapsActivity.this);
            dialog.setMessage("Rota bulunuyor.Lütfen bekleyiniz...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... strings) {

            HttpDataHandlerGeocoding http = new HttpDataHandlerGeocoding();
            String response;
            String data = "";
            // Origin of route
            String str_origin = "origin=" + lat + "," + lon;
//            String str_origin = "origin=" + enlem + "," + boylam;
            // Destination of route
            String str_dest = "destination="+ lat + "," + lon;
//            String str_dest = "destination=" + enlem + "," + boylam;

            // Sensor enabled
            String sensor = "sensor=false";

            for (int i = 0; i < markerPoints.size(); i++) {
                LatLng point = (LatLng) markerPoints.get(i);
                waypoints += point.latitude + "," + point.longitude + "|";
            }

            String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + waypoints + "&" + key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
            data = http.getHttpData(url);
//            ParserTask parserTask = new ParserTask();
//
//            // Invokes the thread for parsing the JSON data
//            parserTask.execute(data);
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(data);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            dialog.dismiss();

            ArrayList<Integer> mrkpoints = new ArrayList<>();
            mrkpoints = waylist;

            if (waylist.size() != 0) {
                for (int i = 0; i < mrkpoints.size(); i++) {
                    LatLng point = (LatLng) markerPoints.get(mrkpoints.get(i));
                    String adres = rotaAdresleri.get(mrkpoints.get(i));
                    AdresKoorModel akm = new AdresKoorModel();
                    akm.setAdres(adres);
                    akm.setPoint(point);
                    optimizeRota.add(akm);
                }
            } else {
                for (int i = 0; i < markerPoints.size(); i++) {
                    LatLng point = (LatLng) markerPoints.get(i);
                    String adres = rotaAdresleri.get(i);
                    AdresKoorModel akm = new AdresKoorModel();
                    akm.setAdres(adres);
                    akm.setPoint(point);
                    optimizeRota.add(akm);
                }
            }
            if (optimizeRota.size() != 0) {
                StringBuilder uri = new StringBuilder(); //TODO BURAYA BAKILACAK
//        /41.09429023,29.08940748
                StringBuilder yeni = uri.append("https://www.google.com/maps/dir/");// Navigasyon için google mapse aktarılıyor
                yeni.append(lat + "," + lon+ "/");
                if (optimizeRota.size() <= 23) {
                    for (int i = 0; i < optimizeRota.size(); i++) {
                        AdresKoorModel akm = optimizeRota.get(i);
                        yeni.append(akm.getPoint().latitude).append(",").append(akm.getPoint().longitude).append("/");
                    }
                } else {
                    for (int i = 0; i < 23; i++) {
                        AdresKoorModel akm = optimizeRota.get(i);
                        yeni.append(akm.getPoint().latitude).append(",").append(akm.getPoint().longitude).append("/");
                    }

                }
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(yeni.toString()));
                startActivity(intent);
            }
        }
    }
    public class DirectionsJSONParser {

        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;
            JSONArray waypointOrder = null;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /* Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    waypointOrder = ((JSONObject) jRoutes.get(i)).getJSONArray("waypoint_order");
                    for (int w = 0; w < waypointOrder.length(); w++) {
                        waylist.add((int) waypointOrder.get(w));
                    }
                    List path = new ArrayList<HashMap<String, String>>();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
            return routes;
        }
    }
}