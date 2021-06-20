package com.finalproject.ncovitrackerproject;

import android.content.Context;

public class DataLocalManager {
    private static final String PREF_EMAIL_LOGIN="PREF_EMAIL_LOGIN";
    private static DataLocalManager instance;
    private SharedPreference sharedPreference;
    public static void init(Context context){
instance =new DataLocalManager();
instance.sharedPreference= new SharedPreference(context);

    }
    public static DataLocalManager getInstance(){
        if(instance==null){
            instance=new DataLocalManager();

        }return instance;
    }
    public static void setEmailLogin(String emailLogin){
        DataLocalManager.getInstance().sharedPreference.putString(PREF_EMAIL_LOGIN,emailLogin);
    }
    public static String getEmailLogin(){
        return DataLocalManager.getInstance().sharedPreference.getString(PREF_EMAIL_LOGIN);
    }
}
