package com.applaudo.homework.homework.utilities;

/**
 * Created by jimi on 21/10/2017.
 * Bean para obtener datos para poblar la lista de MainTeamActivity
 */

public class BeanTeamItemList {

    private String teamLogo;
    private String teamName;
    private String teamAddress;

    public String getTeamLogo() {
        return teamLogo;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamAddress() {
        return teamAddress;
    }

    public BeanTeamItemList(String teamLogo, String teamName, String teamAddress) {
        this.teamLogo = teamLogo;
        this.teamName = teamName;
        this.teamAddress = teamAddress;
    }


}
