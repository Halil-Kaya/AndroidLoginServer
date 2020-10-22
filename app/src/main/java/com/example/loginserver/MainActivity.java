package com.example.loginserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginserver.local.SharedPreferenc;
import com.example.loginserver.model.Token;
import com.example.loginserver.model.UserLogin;
import com.example.loginserver.service.ApiClient;
import com.example.loginserver.service.RestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnGiris;
    Button btnKaydol;
    EditText etMail;
    EditText etSifre;

    RestInterface restInterface;

    public SharedPreferences settings;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("dosya",MODE_PRIVATE);
        initilazeWidgets();

        System.out.println("....................." + settings.getString("refreshToken","a"));


        if(!settings.getString("refreshToken","null").equals("null")){
            kullaniciEkraninaGit();
        }





        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserLogin userLogin = new UserLogin();
                userLogin.setEmail(etMail.getText().toString());
                userLogin.setPassword(etSifre.getText().toString());

                restInterface = ApiClient.getClient().create(RestInterface.class);
                Call<Token> userLoginCall = restInterface.getToken(userLogin);

                userLoginCall.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {

                        Token token = response.body();

                        if(token != null){
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putString("token",token.getToken());
                            editor.putString("mail",etMail.getText().toString());
                            editor.putString("sifre",etSifre.getText().toString());
                            editor.putString("refreshToken",token.getRefreshToken());
                            editor.commit();

                            kullaniciEkraninaGit();
                        }else{

                            Toast.makeText(MainActivity.this,"mail gecerli degil @ isareti yok",Toast.LENGTH_LONG).show();

                        }


                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"mail veya şifre yanlis",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


        btnKaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),KaydolmaEkrani.class);
                startActivity(intent);


            }
        });

    }


    public void initilazeWidgets(){
        btnGiris = findViewById(R.id.btnGiris);
        btnKaydol = findViewById(R.id.btnKaydol);
        etMail = findViewById(R.id.etMail);
        etSifre = findViewById(R.id.etSifre);
    }


    public void kullaniciEkraninaGit(){
        Intent intent = new Intent(getApplicationContext(),kullaniciEkrani.class);
        startActivity(intent);
        finish();
    }



}