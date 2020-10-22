package com.example.loginserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginserver.local.SharedPreferenc;
import com.example.loginserver.model.RefreshToken;
import com.example.loginserver.model.Token;
import com.example.loginserver.model.User;
import com.example.loginserver.service.ApiClient;
import com.example.loginserver.service.RestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class kullaniciEkrani extends AppCompatActivity {

    TextView tvId;
    TextView tvMail;
    TextView tvSifre;
    TextView tvName;
    TextView tvSurname;
    TextView tvRefreshToken;
    TextView tvRefreshTokenDate;

    Button btnCikis;

    RestInterface restInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_ekrani);
        initilazeWidgets();

        SharedPreferences settings = getSharedPreferences("dosya",MODE_PRIVATE);


        String token = settings.getString("token","null");
        System.out.println("tokeeeeennnn: " + token);

        restInterface = ApiClient.getClient().create(RestInterface.class);

        Call<User> userCall = restInterface.getUser("Bearer " + token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user = response.body();
                if(user.getEmail() != null){

                    System.out.println("--userid: " + user.getEmail().toString());
                    tvId.setText(user.getId().toString());
                    tvMail.setText(user.getEmail().toString());
                    tvSifre.setText(user.getPassword().toString());
                    tvName.setText(user.getName().toString());
                    tvSurname.setText(user.getSurName().toString());
                    tvRefreshToken.setText(user.getRefreshToken().toString());
                    tvRefreshTokenDate.setText(user.getRefreshTokenEndDate().toString());

                }else{


                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("token",null);
                    editor.putString("mail",null);
                    editor.putString("sifre",null);
                    editor.putString("refreshToken",null);
                    editor.commit();

                    Intent intent = new Intent(kullaniciEkrani.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setRefreshToken(settings.getString("refreshToken","null"));
                Call<Token> refTokenCall = restInterface.refreshToken(refreshToken);
                refTokenCall.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("token",response.body().getToken());
                        editor.commit();
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Intent intent = new Intent(kullaniciEkrani.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });



        btnCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("token",null);
                editor.putString("mail",null);
                editor.putString("sifre",null);
                editor.putString("refreshToken",null);
                editor.commit();

                Intent intent = new Intent(kullaniciEkrani.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void initilazeWidgets(){
        tvId = findViewById(R.id.tvId);
        tvMail = findViewById(R.id.tvMail);
        tvSifre = findViewById(R.id.tvSifre);
        tvName = findViewById(R.id.tvName);
        tvSurname = findViewById(R.id.tvSurname);
        tvRefreshToken = findViewById(R.id.tvRefreshToken);
        tvRefreshTokenDate = findViewById(R.id.tvRefreshTokenDate);
        btnCikis = findViewById(R.id.btnCikis);
    }
}