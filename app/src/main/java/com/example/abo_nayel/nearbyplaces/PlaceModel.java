package com.example.abo_nayel.nearbyplaces;

/**
 * Created by Abo-Nayel on 24/10/2017.
 */

public class PlaceModel {
    String  name,types[];
    float rating;
    photos[] photos;
    geometry.location location;


    public PlaceModel.photos[] getPhotos() {
        return photos;
    }

    public void setPhotos(PlaceModel.photos[] photos) {
        this.photos = photos;
    }

    class geometry{
        class location{
            float lat,lng;

            public float getLat() {
                return lat;
            }

            public float getLng() {
                return lng;
            }
        }
    }

    class photos{
        String photo_reference;

        public String getPhoto_reference() {
            return photo_reference;
        }

        public void setPhoto_reference(String photo_reference) {
            this.photo_reference = photo_reference;
        }
    }


    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
