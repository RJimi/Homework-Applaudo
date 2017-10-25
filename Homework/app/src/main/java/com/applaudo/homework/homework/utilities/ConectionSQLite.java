package com.applaudo.homework.homework.utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by jimi on 21/10/2017.
 * Creación y acceso a la base de datos
 */

public class ConectionSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "databaseUEFA";
    private static final int DATABASE_VERSION = 1;

    public ConectionSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creamos las tablas
        db.execSQL("CREATE TABLE 'Teams' (" +
                "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "'id_team' INTEGER," +
                "'team_name' TEXT," +
                "'since' TEXT," +
                "'coach' TEXT," +
                "'team_nickname' TEXT," +
                "'stadium' TEXT," +
                "'img_logo' TEXT," +
                "'img_stadium' TEXT," +
                "'latitude' TEXT," +
                "'longitude' TEXT," +
                "'website' TEXT," +
                "'tickets_url' TEXT," +
                "'address' TEXT," +
                "'phone_number' TEXT," +
                "'description' TEXT," +
                "'video_url' TEXT" +
                ");");
        db.execSQL("CREATE TABLE 'schedule_games' (" +
                "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "'team_id' INTEGER," +
                "'date' TEXT," +
                "'stadium' TEXT" +
                ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
    //getContTean verifica si hay datos en la base de datos
    public int getContTeams(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        int count;
        c = db.rawQuery("SELECT COUNT(*) FROM Teams",null);
        c.moveToFirst();
        if (c.isNull(0)){
            count = 0;
        }else{
            count = c.getInt(0);
        }
        c.close();
        db.close();
        return count;
    }
    //Insertamos datos de los equipos
    public void insertTeams(int id, String team_name,String since, String coach, String team_nickname, String stadium, String img_logo, String img_stadium, String latitude, String longitude, String website, String tickets_url, String address, String phone_number, String description, String video_url){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c = db.rawQuery("INSERT INTO Teams VALUES(null, "+id+",'"+team_name+"', '"+since+"', '"+coach+"', '"+team_nickname+"', '"+stadium+"', '"+img_logo+"', '"+img_stadium+"', '"+latitude+"', '"+longitude+"', '"+website+"', '"+tickets_url+"', '"+address+"', '"+phone_number+"', '"+description+"', '"+video_url+"')",null);
        c.moveToFirst();
        c.close();
        db.close();
    }
    //Insertar datos de las fechas de los juegos
    public void insertScheduleGames(int team_id,String date, String stadium){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c = db.rawQuery("INSERT INTO schedule_games VALUES(null,'"+team_id+"', '"+date+"', '"+stadium+"')" ,null);
        c.moveToFirst();
        c.close();
        db.close();
    }
    //Obtener un array de los datos de los equipos
    public ArrayList<BeanTeam> getTeamsData(){
        ArrayList<BeanTeam> arrayTeam = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        c = db.rawQuery("SELECT * FROM Teams", null);
        c.moveToFirst();
        if (c.getCount()!=0){
            do{
                BeanTeam bean = new BeanTeam(c.getInt(0),c.getInt(1), c.getString(2), c.getString(3),
                        c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8),
                        c.getString(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13),
                        c.getString(14), c.getString(15), c.getString(16));
                arrayTeam.add(bean);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return arrayTeam;
    }
    // Obtener un array de las fechas de los partidos
    public ArrayList<BeanSchedule> getScheduleData(int id){
        ArrayList<BeanSchedule> arraySchedule = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        c = db.rawQuery("SELECT id, date, stadium FROM schedule_games WHERE team_id ="+id, null);
        c.moveToFirst();
        if (c.getCount()!=0){
            do{
                BeanSchedule bean = new BeanSchedule(c.getInt(0), c.getString(1), c.getString(2));
                arraySchedule.add(bean);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return arraySchedule;
    }
    //Obtener imagen, nombre y logo de los equipos para pobrar la lista de MainTeamActivity
    public ArrayList<BeanTeamItemList> getTeamItemList(){
        ArrayList<BeanTeamItemList> arrayItemList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        c = db.rawQuery("SELECT img_logo, team_name, address FROM Teams", null);
        c.moveToFirst();
        if (c.getCount()!=0){
            do{
                BeanTeamItemList bean = new BeanTeamItemList(c.getString(0), c.getString(1), c.getString(2));
                arrayItemList.add(bean);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return arrayItemList;
    }
}
