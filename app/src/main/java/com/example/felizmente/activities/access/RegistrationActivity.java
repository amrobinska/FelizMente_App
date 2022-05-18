package com.example.felizmente.activities.access;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;
import com.example.felizmente.beans.User;
import com.example.felizmente.io.UserApiService;
import com.google.gson.GsonBuilder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    Context context;
    EditText emailInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = this;
        getSupportActionBar().hide();
    }

    public void addUser(View view){

        emailInput = findViewById(R.id.userEmailAddress);
        String userEmail = emailInput.getText().toString().toLowerCase();

        passwordInput = findViewById(R.id.userPassword);
        String password = passwordInput.getText().toString();

        if (registrationChecker(userEmail, password)) {
            checkUserAndAddIfNotExists(userEmail, password);
        }
    }

    private boolean registrationChecker(String userEmail, String password) {
        if(userEmail.isEmpty()) {
            emailInput.setError("Falta el usuario (email)");
            emailInput.requestFocus();
            return false;
        } else if (!validateEmail(userEmail)) {
            emailInput.setError("Formato de email erróneo.");
            emailInput.setText("");
            return false;
        }

        if(password.isEmpty()){
            passwordInput.setError("Falta la contraseña");
            passwordInput.requestFocus();
            return false;
        } else if (!validatePassword(password)) {
            passwordInput.setError("La contraseña debe tener entre 8 y 20 caracteres e " +
                    "incluir al menos un número, una mayúscula, una minúscula y uno de estos " +
                    "caracteres: @#$%");
            passwordInput.setText("");
            return false;
        }

        return true;
    }

    private void checkUserAndAddIfNotExists(String userEmail, String userPassword) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/felizmente/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .build();
        UserApiService userApiService = retrofit.create(UserApiService.class);
        Call<User> call = userApiService.search(userEmail);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response != null && response.body() !=null) {
                    if (response.isSuccessful()) {
                        User u = (User) response.body();
                        Log.d("user es:", u.toString());
                        emailInput.setText("");
                        passwordInput.setText("");
                        emailInput.requestFocus();
                        Toast.makeText(context, "Ese nombre de usuario ya existe.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Log.d("404", "User not found, proceed to register");
                    User u = new User(null, userEmail, userPassword);
                    UserApiService userApiServiceAdd = retrofit.create(UserApiService.class);
                    Call<User> addCall = userApiServiceAdd.addUser(u);
                    addCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response != null && response.body() !=null) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context
                                            , "Usuario registrado correctamente."
                                            , Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(context, LoginActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            if (t != null) {
                                t.printStackTrace();
                            }
                        }
                    });
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

    public void goBackToLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // copiado de https://java2blog.com/validate-password-java/
    public boolean validatePassword(String password){
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // https://www.baeldung.com/java-password-hashing
    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return new String(hash);
    }

    //  Sacado de https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
    public boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        return mat.matches();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}