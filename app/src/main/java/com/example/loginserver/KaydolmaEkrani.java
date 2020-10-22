package com.example.loginserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginserver.model.User;
import com.example.loginserver.model.UserRegister;
import com.example.loginserver.service.ApiClient;
import com.example.loginserver.service.RestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaydolmaEkrani extends AppCompatActivity {

    EditText tvMailKaydol;
    EditText tvSifreKaydol;
    EditText tvNameKaydol;
    EditText tvSurnameKaydol;
    Button btnKayit;

    RestInterface restInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydolma_ekrani);

        initilazeWidgets();

        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserRegister userRegister = new UserRegister();
                userRegister.setEmail(tvMailKaydol.getText().toString());
                userRegister.setName(tvNameKaydol.getText().toString());
                userRegister.setPassword(tvSifreKaydol.getText().toString());
                userRegister.setSurName(tvSurnameKaydol.getText().toString());

                restInterface = ApiClient.getClient().create(RestInterface.class);
                Call<User> userCall = restInterface.createUser(userRegister);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User user = response.body();

                        if(user == null){
                            Toast.makeText(KaydolmaEkrani.this,"kayit başarili değil",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(KaydolmaEkrani.this,"kayit başarili",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(KaydolmaEkrani.this,"kayit başarili değil",Toast.LENGTH_LONG).show();

                    }
                });


            }
        });


    }

    public void initilazeWidgets(){

        tvMailKaydol = findViewById(R.id.tvMailKaydol);
        tvSifreKaydol = findViewById(R.id.tvSifreKaydol);
        tvNameKaydol = findViewById(R.id.tvNameKaydol);
        tvSurnameKaydol = findViewById(R.id.tvSurnameKaydol);
        btnKayit = findViewById(R.id.btnKayit);
    }

}