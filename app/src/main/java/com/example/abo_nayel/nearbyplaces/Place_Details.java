package com.example.abo_nayel.nearbyplaces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abo_nayel.nearbyplaces.Model.PlaceModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class Place_Details extends AppCompatActivity {

    ImageView im,map;
    double lat,lng;
    String name ;
    RatingBar r;
    TextView nam , ty;
    Button fav,nav;
    PlaceModel placeModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__details);
        im = (ImageView)findViewById(R.id.imageView);
        map = (ImageView)findViewById(R.id.sMap);
        nam = (TextView)findViewById(R.id.nametxt);
        ty = (TextView)findViewById(R.id.typetxt);
        fav = (Button) findViewById(R.id.favour);
        nav = (Button)findViewById(R.id.navbtn) ;
        r= (RatingBar)findViewById(R.id.ratingBar);

        SharedPreferences sharedPreferences = getSharedPreferences("fav data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        Gson gson = new Gson();
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                placeModel = new PlaceModel();
            }
        });

         lat = getIntent().getDoubleExtra("lat",0);
         lng = getIntent().getDoubleExtra("lng",0);
         name = getIntent().getStringExtra("name");
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();

        final String name = getIntent().getStringExtra("name");
        String type = getIntent().getStringExtra("type");
        String ph_ref = getIntent().getStringExtra("ph ref");
        float rat = getIntent().getFloatExtra("rat",0);
        //final int id = getResources().getIdentifier("com.example.abo_nayel.nearbyplaces:drawable/fav_red.xml" , null, null);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav.setBackground(getResources().getDrawable(R.drawable.fav_red));
            }
        });

        Picasso.with(getApplicationContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=250&photoreference="+
                ph_ref+"&key=AIzaSyDi9JdUYE28KGzwm5t-dLONa4zIaVne6jc").into(im);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });

        nam.setText(name);
        ty.setText(type);
        r.setRating(rat);
        Picasso.with(getApplicationContext()).load("https://maps.googleapis.com/maps/api/staticmap?" +
                "zoom=13&size=360x80&maptype=roadmap&markers=color:blue|label:S|"+lat+","+lng+"&markers" +
                "yDi9JdUYE28KGzwm5t-dLONa4zIaVne6jc").into(map);
        Toast.makeText(getApplicationContext(),lat+","+lng,Toast.LENGTH_SHORT).show();

    }
}
