package com.example.felizmente.activities.access;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;
import com.example.felizmente.activities.MainActivity;
import com.example.felizmente.beans.User;
import com.example.felizmente.db.ControladorDB;
import com.example.felizmente.io.UserApiService;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ControladorDB db;
    private EditText emailBox, passBox;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        context = this;
    }

    public void goToRegistration(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        db= new ControladorDB(this);

        emailBox = findViewById(R.id.user);
        String user = emailBox.getText().toString();

        passBox = findViewById(R.id.userPass);
        String pass = passBox.getText().toString();

        if (loginChecker(user, pass)) {
            checkIfExistsAndLogin(user, pass);
        }
    }

    private void checkIfExistsAndLogin(String user, String pass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/felizmente/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .build();
        UserApiService userApiService = retrofit.create(UserApiService.class);
        User u = new User(user, pass);
        Call<User> call = userApiService.searchCoincidence(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response != null && response.body() !=null) {
                    if (response.isSuccessful()) {
                        User u = (User) response.body();
                        Log.d("user is:", u.toString());
                        Toast.makeText(context, "Login con éxito", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(context, MainActivity.class));
                    }
                } else {
                    Log.d("404", "User not found");
                    Toast.makeText(context, "Login incorrecto. Revise sus credenciales.", Toast.LENGTH_LONG).show();
                    emailBox.setText("");
                    passBox.setText("");
                    emailBox.requestFocus();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(t!=null)
                {
                    t.printStackTrace();
                }
            }
        });
    }

    private boolean loginChecker(String user, String pass) {
        if(user.isEmpty()){
            emailBox.setError("Falta el usuario (email)");
            emailBox.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            passBox.setError("Falta la contraseña");
            passBox.requestFocus();
            return false;
        }
        return true;
    }

}