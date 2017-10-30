package com.example.abo_nayel.nearbyplaces;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abo_nayel.nearbyplaces.Model.PlaceModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Search extends AppCompatActivity {

    String[] types = {
            "accounting",
            "airport",
            "amusement_park",
            "aquarium",
            "art_gallery",
            "atm",
            "bakery",
            "bank",
            "bar",
            "beauty_salon",
            "bicycle_store",
            "book_store",
            "bowling_alley",
            "bus_station",
            "cafe",
            "restaurant"
    };
    ListView places;
    EditText searchtxt;
    Button searchbtn,logout , fav;
    TextView t1,t2;
     PlaceModel[] placeModels;
    double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lat = getIntent().getDoubleExtra("lat",0);
        lng = getIntent().getDoubleExtra("lng",0);
        Toast.makeText(getApplicationContext(),lat+","+lng,Toast.LENGTH_SHORT).show();
        places = (ListView)findViewById(R.id.searchList);
        searchtxt = (EditText)findViewById(R.id.searchtxt);
        searchbtn =(Button)findViewById(R.id.searchbtn);
        logout = (Button)findViewById(R.id.logoutbtn);
        fav = (Button)findViewById(R.id.favbtn);
        t1 = (TextView)findViewById(R.id.textView2) ;
        t2 = (TextView)findViewById(R.id.textView6) ;

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Search.this,Place_Details.class);
                startActivity(i);
            }
        });

//        places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Search.this,placeModels[position].getName(),Toast.LENGTH_LONG).show();
//            }
//        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Search.this,Login.class);
                startActivity(i);
                finish();
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.setText("Nearby Places");
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type="+
                        searchtxt.getText()+"&keyword=cruise&key=AIzaSyDi9JdUYE28KGzwm5t-dLONa4zIaVne6jc";
                excuteWebServices(url);
            }
        });
    }
    void excuteWebServices(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    placeModels = new Gson().fromJson(jsonArray.toString(),PlaceModel[].class);
                    placeAdapter placeAdapter = new placeAdapter(Search.this,placeModels);
                    places.setAdapter(placeAdapter);
                    places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getApplicationContext(), Place_Details.class);

                            i.putExtra("this place",(Parcelable) placeModels[position]);
                            i.putExtra("lat" , placeModels[position].getGeometry().getLocation().getLat());
                            i.putExtra("lng" ,placeModels[position].getGeometry().getLocation().getLng() );
                            i.putExtra("rat",placeModels[position].getRating().floatValue());
                            i.putExtra("name", placeModels[position].getName());
                            i.putExtra("type",placeModels[position].getTypes().get(1));
                            i.putExtra("ph ref", placeModels[position].getPhotos().get(0).getPhotoReference());

                            startActivity(i);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
}
