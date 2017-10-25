package com.applaudo.homework.homework;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.applaudo.homework.homework.fragments.MainFragment;
import com.applaudo.homework.homework.utilities.ConectionSQLite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by jimi on 21/10/2017.
 * Actividad principal muestra una imagen de bienvenida
 */

public class SplashScreenActivity extends AppCompatActivity {

     private static final String STRING_URL = "http://applaudostudios.com/external/applaudo_homework.json";
    URL url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Verificamos si hay conección a internet
        if (comprobarConectividad()){
            ConectionSQLite conexionDB = new ConectionSQLite(getApplicationContext());
            if(conexionDB.getContTeams()==0){
                try{
                    url = new URL(STRING_URL);
                    //Descargamos y poblamos la base de datos
                    DownloadDataTask tarea = new DownloadDataTask();
                    tarea.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                //Ya tenemos la base de datos esperamos 3 segundos
                Thread timerThread = new Thread(){
                    public void run(){
                        try {
                            sleep(3000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }finally {
                            LanzarMain();
                        }
                    }
                };
                timerThread.start();
            }
            conexionDB.close();

        }else{
            Toast.makeText(getApplicationContext(), "Intentelo mas tarde", Toast.LENGTH_LONG).show();
        }
    }
    //Lanzamos actividad que contiene la lista
    public void LanzarMain(){
        Intent intent = new Intent(this, MainFragment.class);
        startActivity(intent);
        finish();
    }
    //Comprobamos si hay acceso a internet
    private boolean comprobarConectividad(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info == null || !info.isConnected() || !info.isAvailable()){
            Toast.makeText(getApplicationContext(), "Ooops! No tienes conexión a internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private class DownloadDataTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... param) {
            try {
                InputStream inputStream = url.openStream();
                Scanner scanner = new Scanner(inputStream, "UTF-8");
                String string = "";
                while (scanner.hasNext()){
                    string += scanner.nextLine();
                }
                scanner.close();
                //Quitamos los apostrofes para que no haya problema al ingresar la información
                String sinApostrofe = string.replaceAll("'", " ");
                JSONArray jsonArray = new JSONArray(sinApostrofe);

                ConectionSQLite conectionSQLite= new ConectionSQLite(getApplicationContext());
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //Llenamos la base de datos con los datos de los equipos
                    conectionSQLite.insertTeams(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.get("team_name").toString(),
                            jsonObject.get("since").toString(), jsonObject.get("coach").toString(),jsonObject.get("team_nickname").toString(),
                            jsonObject.get("stadium").toString(), jsonObject.get("img_logo").toString(),jsonObject.get("img_stadium").toString(),
                            jsonObject.get("latitude").toString(),jsonObject.get("longitude").toString(),jsonObject.get("website").toString(),
                            jsonObject.get("tickets_url").toString(),jsonObject.get("address").toString(),jsonObject.get("phone_number").toString(),
                            jsonObject.get("description").toString(),jsonObject.get("video_url").toString());

                    JSONArray jsonArrayGames = jsonObject.getJSONArray("schedule_games");
                    for (int j=0; j<jsonArrayGames.length(); j++){
                        JSONObject jsonObjectSchedule = jsonArrayGames.getJSONObject(j);
                        //Llenamos la base de datos con los las fechas de los partidos
                        conectionSQLite.insertScheduleGames(Integer.parseInt(jsonObject.get("id").toString()),
                                jsonObjectSchedule.get("date").toString(), jsonObjectSchedule.get("stadium").toString());
                        Log.d("SCHEDULE", Integer.parseInt(jsonObject.get("id").toString())+"--"+jsonObjectSchedule.get("date").toString()+"--"+jsonObjectSchedule.get("stadium").toString());
                    }
                }
                conectionSQLite.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            return null;
        }
        protected void onProgressUpdate(Integer... progress) {

        }
        protected void onPostExecute(Long result) {
            LanzarMain();
        }
    }

}
