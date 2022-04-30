package com.example.felizmente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felizmente.db.ControladorDB;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginActivity extends AppCompatActivity {

    private ControladorDB db;
    private EditText userBox, passBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void goToRegistration(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void login(View view) throws InvalidKeySpecException, NoSuchAlgorithmException {
        db= new ControladorDB(this);

        userBox = findViewById(R.id.user);
        String user = userBox.getText().toString();

        passBox = findViewById(R.id.userPass);
        String pass = passBox.getText().toString();

        if(user.isEmpty()){
            userBox.setError("Falta el usuario");
            userBox.requestFocus();
        } else if (pass.isEmpty()){
            passBox.setError("Falta la contraseña");
            passBox.requestFocus();
        } else if (!db.userExists(user)){
            Toast toast = Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_LONG);
            toast.show();
            userBox.setText("");
            passBox.setText("");
            userBox.requestFocus();
        } else if(!db.login(user,pass).isEmpty()){
//            pass = hashPassword(pass);
            db.login(user,pass);
            startActivity(new Intent(this, MainActivity.class).putExtra("user", user));
        } else {
            Toast toast = Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_LONG);
            toast.show();
            userBox.setText("");
            passBox.setText("");
            userBox.requestFocus();
        }
    }

    // ESTÁ DANDO PROBLEMAS, EN EL REGISTRATION BIEN, PERO EN CUANTO LO METO AQUI PETA LA APP
    // esto está tambien en el registration
    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return new String(hash);
    }
}