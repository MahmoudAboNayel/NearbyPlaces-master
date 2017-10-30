package com.example.abo_nayel.nearbyplaces;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.abo_nayel.nearbyplaces.Model.PlaceModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Abo-Nayel on 24/10/2017.
 */

public class placeAdapter extends ArrayAdapter {
    public placeAdapter(@NonNull Context context, @NonNull PlaceModel[] objects) {
        super(context, 0, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_place_raw, parent, false);
        }


        final PlaceModel placeModel = (PlaceModel) getItem(position);
        Button nav = (Button) convertView.findViewById(R.id.button2);
        nav.setFocusable(false);

        TextView name = (TextView) convertView.findViewById(R.id.Pname);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        RatingBar r = (RatingBar) convertView.findViewById(R.id.ratingBar);
        ImageView i = (ImageView) convertView.findViewById(R.id.photo);
        if (placeModel.getPhotos().get(0).getPhotoReference().equals(null)) {
        }else Picasso.with(getContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=40&photoreference=" +
                placeModel.getPhotos().get(0).getPhotoReference() + "&key=AIzaSyDi9JdUYE28KGzwm5t-dLONa4zIaVne6jc").into(i);
//        Toast.makeText(getContext(),placeModel.photos[0].getPhoto_reference(),Toast.LENGTH_SHORT).show();
//        r.setIsIndicator(true);
        r.setRating(placeModel.getRating().floatValue());
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getContext(),MapsActivity.class);
                i.putExtra("lat",placeModel.getGeometry().getLocation().getLat());
                i.putExtra("lat",placeModel.getGeometry().getLocation().getLng());
                i.putExtra("name",placeModel.getName());
                getContext().startActivity(i);
            }
        });
        type.setText(placeModel.getTypes().get(1));
        name.setText(placeModel.getName());
        return convertView;
    }
}

