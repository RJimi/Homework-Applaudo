package com.applaudo.homework.homework;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.applaudo.homework.homework.utilities.BeanSchedule;
import com.applaudo.homework.homework.utilities.BeanTeam;
import com.applaudo.homework.homework.utilities.ConectionSQLite;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by jimi on 21/10/2017.
 * Actividad que muestra el detalle
 */

public class TeamDetailScreenActivity extends Fragment implements OnMapReadyCallback {

    private VideoView videoView;
    private ImageView stadiumImage;
    private String dataTeam, descripcion;
    private TextView teamName, detailTeam, descriptionTeam;
    private MapView map;
    Context context;
    Activity activity;
    Bundle mapViewBundle = null;
    private ArrayList<BeanTeam> arratTeam;
    private ArrayList<BeanSchedule> arraySchedule;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static String ARG_ID = "id_team";
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {
        context = getActivity();
        View vista = inflater.inflate(R.layout.activity_team_detail_screen, contenedor, false);
        Bundle arg = getArguments();

        videoView = vista.findViewById(R.id.videoView);
        stadiumImage = vista.findViewById(R.id.teamStadiumImageV);
        teamName = vista.findViewById(R.id.teamNameDetailTV);
        detailTeam = vista.findViewById(R.id.detailTextView);
        descriptionTeam = vista.findViewById(R.id.descriptionTeamTxtV);
        map = vista.findViewById(R.id.map);

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        if(arg != null){
            position = arg.getInt(ARG_ID);
            showDetailData(position, vista);
        }else {
            showDetailData(0, vista);
        }

        return vista;
    }
    public void showDetailData(int id){
        showDetailData(id, getView());
    }
    public void showDetailData(int id, View vista){

        arratTeam = new ArrayList<>();
        arraySchedule = new ArrayList<>();
        ConectionSQLite conectionSQLite = new ConectionSQLite(context);
        arratTeam = conectionSQLite.getTeamsData();
        arraySchedule = conectionSQLite.getScheduleData(id);
        conectionSQLite.close();
        //Mostrar video

        Uri uri=Uri.parse(arratTeam.get(id).getVideo_url());

        vista.getParent();
        videoView.setVideoURI(uri);
        videoView.start();

        //Mostrar imagen del estadio
        Glide.with(context)
                .load(arratTeam.get(id).getImg_stadium())
                .into(stadiumImage);
        //Mostrar Datos
        teamName.setText(arratTeam.get(id).getTeam_name());
        dataTeam = arratTeam.get(id).getDescription();
        descripcion = " Plays date           Stadium \n";
        for (int i=0; i<arraySchedule.size(); i++){
            descripcion += arraySchedule.get(i).getDate()+" "+arraySchedule.get(i).getStadium()+"\n";
            Log.d("SCHEDULE",arraySchedule.get(i).getDate()+" -->"+arraySchedule.get(i).getStadium()+"--"+arraySchedule.get(i).getTeam_id() );
        }

        detailTeam.setText(descripcion);
        detailTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSchedule(context,descripcion,"Vamos a uno de estos juegos!!!");
            }
        });
        descriptionTeam.setText(dataTeam);
        //MostrarMapa
        map.onCreate(mapViewBundle);
        map.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(arratTeam.get(position).getLatitude()),
                Double.parseDouble(arratTeam.get(position).getLongitude()))).title(arratTeam.get(position).getStadium()));
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        map.onSaveInstanceState(mapViewBundle);
    }
    public static void shareSchedule(Context context, String Share, String titleShare){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, titleShare);
        intent.putExtra(Intent.EXTRA_TEXT, Share);

        context.startActivity(Intent.createChooser(intent, "Compartir"));
    }
    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
    }
    @Override
    public void onPause() {
        map.onPause();
        super.onPause();
    }
}
