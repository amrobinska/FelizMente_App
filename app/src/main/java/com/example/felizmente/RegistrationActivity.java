package com.example.felizmente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felizmente.db.ControladorDB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    ControladorDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        db = new ControladorDB(this);
        //getSupportActionBar().hide();
    }

    public void addUser(View view) throws NoSuchAlgorithmException {

        EditText userNameInput = findViewById(R.id.userName);
        String user = userNameInput.getText().toString().toLowerCase();

        EditText emailInput = findViewById(R.id.userEmailAddress);
        String email = emailInput.getText().toString().toLowerCase();

        EditText passwordInput = findViewById(R.id.userPassword);
        String password = passwordInput.getText().toString();

        boolean exists = db.userExists(user);

        if(!user.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(!validateEmail(email)) {
                emailInput.setError("Formato de email erróneo.");
                emailInput.setText("");
            } else if(!validatePassword(password)){
                passwordInput.setError("La contraseña debe tener entre 8 y 20 caracteres e " +
                        "incluir al menos un número, una mayúscula, una minúscula y uno de estos " +
                        "caracteres: @#$%");
                passwordInput.setText("");
            } else {
                if(!exists){
                    password = hashPassword(password);
                    db.addUser(user, email, password);
                    Toast.makeText(this, "Usuario registrado correctamente.", Toast.LENGTH_LONG).show();
                } else {
                    userNameInput.setText("");
                    emailInput.setText("");
                    passwordInput.setText("");
                    userNameInput.requestFocus();
                    Toast.makeText(this, "Ese nombre de usuario ya existe.", Toast.LENGTH_LONG).show();
                }
            }
        } else if(user.isEmpty()){
            userNameInput.setError("Falta el nombre");
            userNameInput.requestFocus();
        } else if(email.isEmpty()){
            emailInput.setError("Falta el email");
            emailInput.requestFocus();
        } else {
            passwordInput.setError("Falta la contraseña");
            passwordInput.requestFocus();
        }

        // ESTARIA GUAY SI PUDIESEMOS AÑADIR UN CHECKBOX PARA MANTERNERSE LOGGEADO O SI LA APP LO HICIESE AUTOMATICAMENTE (COMPROBAR ESTO)
    }


    // copiado de https://java2blog.com/validate-password-java/
    public boolean validatePassword(String password){
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword);
    }

    //  Sacado de https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
    public boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        return mat.matches();
    }
}