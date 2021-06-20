package com.finalproject.ncovitrackerproject.Shared_Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String MY_SHARE_REFERENCE = "MY_SHARE_REFERENCE";
    private Context context;

    public SharedPreference(Context context) {
        this.context = context;
    }
    public void putString(String key, String value){
        SharedPreferences shareReference =  context.getSharedPreferences(MY_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shareReference.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getString(String key){
        SharedPreferences sharePreference =  context.getSharedPreferences(MY_SHARE_REFERENCE,Context.MODE_PRIVATE);
    return sharePreference.getString(key,"");
    }
    public void putBoolean(String key, boolean value){
        SharedPreferences shareReference =  context.getSharedPreferences(MY_SHARE_REFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shareReference.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public boolean getBoolean(String key){
        SharedPreferences sharePreference =  context.getSharedPreferences(MY_SHARE_REFERENCE,Context.MODE_PRIVATE);
        return sharePreference.getBoolean(key,false);
    }
}
