package com.example.loginserver.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenc {

    public Context context;
    public SharedPreferences settings;
    public SharedPreferences.Editor editor;

    public SharedPreferenc(Context context){
        this.context = context;
        System.out.println("-------------------------");
        SharedPreferences settings = context.getSharedPreferences("dosya",context.MODE_PRIVATE);

        //String mail = settings.getString("mail",null);
        //System.out.println("mail: " + mail);
        //SharedPreferences.Editor editor = settings.edit().putString("mail","halilkaya");
        //editor.commit();

    }


    public void setToken(String accesToken){
        editor = settings.edit().putString("token",accesToken);
        editor.commit();
    }

    public void setRefreshToken(String refreshToken){
        editor = settings.edit().putString("refreshToken",refreshToken);
        editor.commit();
    }

    public void setMailAndPassword(String mail,String password){
        editor = settings.edit().putString("mail",mail);
        editor = settings.edit().putString("password",password);
        editor.commit();
    }

    public String getToken(){
        return settings.getString("token","null");
    }

    public String getRefreshToken(){

        if(settings.getString("refreshToken","d") == null){
            return "";
        }

        return settings.getString("refreshToken","null");
    }

    public String getMail(){
        return settings.getString("mail","null");
    }

    public String getPassword(){
        return settings.getString("password","null");
    }


}
