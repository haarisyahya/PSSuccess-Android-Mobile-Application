package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private EditText enterLocation;
    private TextView nameResult;
    private TextView addressResult;

    private ArrayList<String> universityNames;
    private ArrayList<Node> universities;
    private RequestQueue requestQueue;
    private static double lat;
    private static double lng;
    private static double mapLat;
    private static double mapLng;
    private ArrayList<Node> universitiesCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        enterLocation = findViewById(R.id.enter_location);
        nameResult = findViewById(R.id.name_result);
        addressResult = findViewById(R.id.address_result);

        universityNames = new ArrayList<>();
        universities = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(HomeActivity.this);

        universityNames.add("Algoma University");
        universityNames.add("Brock University");
        universityNames.add("Carleton University");
        universityNames.add("University of Guelph");
        universityNames.add("Lakehead University");
        universityNames.add("Laurentian University");
        universityNames.add("McMaster University");
        universityNames.add("Nipissing University");
        universityNames.add("OCAD University");
        universityNames.add("Ontario Tech University");
        universityNames.add("University of Ottawa");
        universityNames.add("Queen's University");
        universityNames.add("University of Toronto");
        universityNames.add("Toronto Metropolitan University");
        universityNames.add("Trent University");
        universityNames.add("University of Toronto");
        universityNames.add("Western University");
        universityNames.add("Wilfrid Laurier University");
        universityNames.add("University of Windsor");
        universityNames.add("York University");

        for (String university : universityNames) {
            universities.add(get(university));
        }

        lat = 0;
        lng = 0;
        mapLat = 0;
        mapLng = 0;

        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                universitiesCopy = new ArrayList<>(universities);

                String keywords = enterLocation.getText().toString().replaceAll(" ", "+");
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=AIzaSyCp4mshs0iXKbeoqalbsmo5Ld0sB5B6IMg", keywords);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            JSONObject location = jsonObject.getJSONObject("geometry").getJSONObject("location");
                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");

                            HomeActivity.lat = lat;
                            HomeActivity.lng = lng;

                            set();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(HomeActivity.this, "Invalid/Unrecognized Address", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Toast.makeText(HomeActivity.this, "Invalid/Unrecognized Address", Toast.LENGTH_LONG).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        Button nextNearest = findViewById(R.id.next);
        nextNearest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapLat == 0 && mapLng == 0)
                {
                    Toast.makeText(HomeActivity.this, "No Address Searched", Toast.LENGTH_LONG).show();
                }
                else if (universitiesCopy.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "No Additional Universities", Toast.LENGTH_LONG).show();
                }
                else {
                    set();
                }
            }
        });

        Button viewMap = findViewById(R.id.view_map);
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapLat == 0 && mapLng == 0) {
                    Toast.makeText(HomeActivity.this, "No Address Searched", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                    intent.putExtra("lat", mapLat);
                    intent.putExtra("lng", mapLng);
                    startActivity(intent);
                }
            }
        });
    }

    public Node get(String keywords) {
        Node p = new Node();
        p.name = keywords;

        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=AIzaSyCp4mshs0iXKbeoqalbsmo5Ld0sB5B6IMg", keywords.replaceAll(" ", "+"));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject result = response.getJSONArray("results").getJSONObject(0);

                    String formattedAddress = result.getString("formatted_address");

                    JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");

                    p.setFormattedAddress(formattedAddress);
                    p.setLat(lat);
                    p.setLng(lng);
                    p.setLocation(lat, lng);
                }
                catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(HomeActivity.this, "Invalid/Unrecognized Address", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(HomeActivity.this, "Invalid/Unrecognized Address", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

        return p;
    }

    public void set() {
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);

        Node p = null;
        float distance = -1;

        for (Node university : universitiesCopy) {
            float i = location.distanceTo(university.location);

            if (distance == -1 || i < distance) {
                p = university;
                distance = i;
            }
        }

        nameResult.setText(p.name);
        addressResult.setText(p.formattedAddress);

        mapLat = p.lat;
        mapLng = p.lng;

        universitiesCopy.remove(p);
    }
}