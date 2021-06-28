package com.finalproject.ncovitrackerproject;

import com.finalproject.ncovitrackerproject.Shared_Preferences.DataLocalManager;

public class Constants {
    //Country Data Keys
    public static final String COUNTRY_NAME = "country";
    public static final String COUNTRY_CONFIRMED = "cases";
    public static final String COUNTRY_ACTIVE = "active";
    public static final String COUNTRY_DECEASED = "deaths";
    public static final String COUNTRY_NEW_CONFIRMED = "todayCases";
    public static final String COUNTRY_TESTS = "tests";
    public static final String COUNTRY_NEW_DECEASED = "todayDeaths";
    public static final String COUNTRY_FLAGURL = "flag";
    public static final String COUNTRY_RECOVERED = "recovered";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

}
