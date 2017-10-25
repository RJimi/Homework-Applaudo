package com.applaudo.homework.homework;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.applaudo.homework.homework.fragments.MainFragment;
import com.applaudo.homework.homework.fragments.TeamListAdapter;
import com.applaudo.homework.homework.utilities.BeanTeamItemList;
import com.applaudo.homework.homework.utilities.ConectionSQLite;

import java.util.ArrayList;

/**
 * Created by Jimi on 21/10/2017.
 * Actividad que muestra la lista de equipos
 */

public class MainTeamActivity extends Fragment {
    Context context;
    TeamListAdapter adaptador;
    ArrayList<BeanTeamItemList> teamItemListArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle savedInstanceState) {
       // creamos la vista
        context = getActivity();
        View vista = inflater.inflate(R.layout.activity_team_screen, contenedor, false);
        ListView teamList = vista.findViewById(R.id.teamsListView);
        ConectionSQLite conectionSQLite = new ConectionSQLite(context);
        teamItemListArray = conectionSQLite.getTeamItemList();
        adaptador = new TeamListAdapter(context, teamItemListArray);
        conectionSQLite.close();

        teamList.setAdapter(adaptador);
        teamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  ((MainFragment)context).mostrarDetalle(position);
            }
        });
        return vista;
    }
}
