package com.example.abo_nayel.nearbyplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Place_Details extends AppCompatActivity {

    ImageView im,map;
    TextView nam , ty;
    Button fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__details);
        im = (ImageView)findViewById(R.id.imageView);
        map = (ImageView)findViewById(R.id.sMap);
        nam = (TextView)findViewById(R.id.nametxt);
        ty = (TextView)findViewById(R.id.typetxt);
        fav = (Button) findViewById(R.id.favour);

//        float lat = getIntent().getFloatExtra("lat",(float)0.0);
//        float lng = getIntent().getFloatExtra("lng",(float)0.0);
//        String name = getIntent().getStringExtra("name");
//        String type = getIntent().getStringExtra("type");
//        String ph_ref = getIntent().getStringExtra("ph ref");

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav.setBackground(getDrawable(0));
            }
        });

//        Picasso.with(getApplicationContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=40&photoreference="+
//                ph_ref+"&key=AIzaSyDi9JdUYE28KGzwm5t-dLONa4zIaVne6jc").into(im);
//
//        nam.setText(name);
//        ty.setText(type);
//
//        Picasso.with(getApplicationContext()).load("https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York" +
//                ",NY&zoom=13&size=600x300&maptype=roadmap&markers=color:blue|label:S|"+lat+","+lng+"&markers" +
//                "yDi9JdUYE28KGzwm5t-dLONa4zIaVne6jc").into(map);
//        Toast.makeText(getApplicationContext(),lat+","+lng,Toast.LENGTH_SHORT).show();

    }
}
