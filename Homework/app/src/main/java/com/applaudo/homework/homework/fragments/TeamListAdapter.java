package com.applaudo.homework.homework.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudo.homework.homework.R;
import com.applaudo.homework.homework.utilities.BeanTeamItemList;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * Created by jimi on 24/10/2017.
 * Adaptador para mostrar en MainTeamActivity
 */

public class TeamListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<BeanTeamItemList> TeamItemsArray;
    private Context context;

    public TeamListAdapter(Context context,  ArrayList<BeanTeamItemList> TeamItemsArray){
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.TeamItemsArray = TeamItemsArray;
        this.context=context;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent){
        final ViewHolder holder;
        if(view==null){
            view = inflater.inflate(R.layout.activity_item_team_list, null);
            holder = new ViewHolder();
            holder.imageLogo = view.findViewById(R.id.teamLogoimageView);
            holder.teamName = view.findViewById(R.id.teamNameTextView);
            holder.teamAddress = view.findViewById(R.id.teamAddressTextView);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }


        Glide.with(context)
                .load(TeamItemsArray.get(position).getTeamLogo())
                .into(holder.imageLogo);

        holder.teamName.setText(TeamItemsArray.get(position).getTeamName());
        holder.teamAddress.setText(TeamItemsArray.get(position).getTeamAddress());

        return view;
    }

    private class ViewHolder{
        private ImageView imageLogo;
        private TextView teamName;
        private TextView teamAddress;

    }
    @Override
    public int getCount() {
        return TeamItemsArray.size();
    }

    @Override
    public Object getItem(int position) {
        return TeamItemsArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
