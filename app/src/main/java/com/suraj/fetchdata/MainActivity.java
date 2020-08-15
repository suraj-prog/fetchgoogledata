package com.suraj.fetchdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends ListActivity {
    private String[] placeName;
    private String[] imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetPlaces(this,getListView()).execute();
    }
    class GetPlaces extends AsyncTask<Void, Void, Void> {
        Context context;
        private ListView listView;
        private ProgressDialog bar;

        public GetPlaces(Context context, ListView listView) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.listView = listView;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            bar.dismiss();
            this.listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, placeName));
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            bar = new ProgressDialog(context);
            bar.setIndeterminate(true);
            bar.setTitle("Loading");
            bar.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            findNearLocation();
            return null;
        }
    }
    public void findNearLocation()   {

        PlacesService service = new PlacesService("AIzaSyB29ad61z7gdtdWbV1sAjJLnOOD9vJxUSA");

       List<Places> findPlaces = service.findPlaces(19.992269,73.704100,"");

        placeName = new String[findPlaces.size()];
        imageUrl = new String[findPlaces.size()];

        for (int i = 0; i < findPlaces.size(); i++) {

            Places placeDetail = findPlaces.get(i);
            placeDetail.getIcon();

            System.out.println(placeDetail.getName());
            placeName[i]=placeDetail.getName();
            imageUrl[i]=placeDetail.getIcon();
        }
    }

}