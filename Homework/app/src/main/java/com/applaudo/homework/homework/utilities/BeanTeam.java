package com.applaudo.homework.homework.utilities;

/**
 * Created by jimi on 23/10/2017.
 * Bean para obtener datos de los equipos de la base de datos
 */

public class BeanTeam {

    public int getIdppl() {
        return idppl;
    }

    int idppl;
    int id;
    String team_name;
    String since;
    String coach;
    String team_nickname;
    String stadium;
    String img_logo;
    String img_stadium;
    String latitude;
    String longitude;
    String website;
    String tickets_url;
    String address;
    String phone_number;
    String description;
    String video_url;

    public int getId() {
        return id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public String getSince() {
        return since;
    }

    public String getCoach() {
        return coach;
    }

    public String getTeam_nickname() {
        return team_nickname;
    }

    public String getStadium() {
        return stadium;
    }

    public String getImg_logo() {
        return img_logo;
    }

    public String getImg_stadium() {
        return img_stadium;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getWebsite() {
        return website;
    }

    public String getTickets_url() {
        return tickets_url;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getDescription() {
        return description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public BeanTeam(int idPpl, int id, String team_name, String since, String coach, String team_nickname, String stadium, String img_logo, String img_stadium, String latitude, String longitude, String website, String tickets_url, String address, String phone_number, String description, String video_url) {
        this.idppl=idPpl;
        this.id = id;
        this.team_name = team_name;
        this.since = since;
        this.coach = coach;
        this.team_nickname = team_nickname;
        this.stadium = stadium;
        this.img_logo = img_logo;
        this.img_stadium = img_stadium;
        this.latitude = latitude;
        this.longitude = longitude;
        this.website = website;
        this.tickets_url = tickets_url;
        this.address = address;
        this.phone_number = phone_number;
        this.description = description;
        this.video_url = video_url;
    }



}
