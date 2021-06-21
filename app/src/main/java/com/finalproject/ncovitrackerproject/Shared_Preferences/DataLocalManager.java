package com.finalproject.ncovitrackerproject.Shared_Preferences;

import android.content.Context;

public class DataLocalManager {
    private static final String PREF_EMAIL_LOGIN="PREF_EMAIL_LOGIN";
    private static final String PREF_USER_ID="PREF_USER_ID";
    private static final String PREF_CHECK_INSTALL="PREF_CHECK_INSTALL";
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
    public static void setUserID(String emailLogin){
        DataLocalManager.getInstance().sharedPreference.putString(PREF_USER_ID,emailLogin);
    }
    public static String getUserID(){
        return DataLocalManager.getInstance().sharedPreference.getString(PREF_USER_ID);
    }
    public static void setFirstInstallApp(boolean isFirst){
        DataLocalManager.getInstance().sharedPreference.putBoolean(PREF_CHECK_INSTALL,isFirst);
    }
    public  static boolean getFirstInstallApp(){
        return DataLocalManager.getInstance().sharedPreference.getBoolean(PREF_CHECK_INSTALL);
    }

}
