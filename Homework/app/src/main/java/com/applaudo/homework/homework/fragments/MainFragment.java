package com.applaudo.homework.homework.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.applaudo.homework.homework.MainTeamActivity;
import com.applaudo.homework.homework.R;
import com.applaudo.homework.homework.TeamDetailScreenActivity;

/**
 * Created by jimi on 24/10/2017.
 */

public class MainFragment extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainTeamActivity fragment = new MainTeamActivity();
        fragmentTransaction.replace(R.id.frag_team, fragment);
        fragmentTransaction.commit();

        if (findViewById(R.id.frag_team_detail)!=null){
            FragmentManager fragmentManagerPeque = getFragmentManager();
            FragmentTransaction fragmentTransactionPeque = fragmentManagerPeque.beginTransaction();
            TeamDetailScreenActivity primerFragment = new TeamDetailScreenActivity();
            fragmentTransactionPeque.replace(R.id.frag_team_detail, primerFragment);
            fragmentTransactionPeque.commit();
        }
    }
    //Mostrar TeamDetailScreenActivity
    public void mostrarDetalle(int id){
        FragmentManager fragmentManager = getFragmentManager();
        TeamDetailScreenActivity detail = (TeamDetailScreenActivity)fragmentManager.findFragmentById(R.id.frag_team_detail);
        if(detail != null){
            detail.showDetailData(id);

        }else {
            TeamDetailScreenActivity nuevoFragment = new TeamDetailScreenActivity();
            Bundle args = new Bundle();
            args.putInt(detail.ARG_ID, id);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frag_team, nuevoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
