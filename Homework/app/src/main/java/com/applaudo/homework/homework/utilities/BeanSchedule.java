package com.applaudo.homework.homework.utilities;

/**
 * Created by jimi on 23/10/2017.
 * Bean para obtener datos de la base de datos
 */

public class BeanSchedule {
    int team_id;
    String date;
    String stadium;

    public int getTeam_id() {
        return team_id;
    }

    public String getDate() {
        return date;
    }

    public String getStadium() {
        return stadium;
    }

    public BeanSchedule(int team_id, String date, String stadium) {
        this.team_id = team_id;
        this.date = date;
        this.stadium = stadium;
    }
}
