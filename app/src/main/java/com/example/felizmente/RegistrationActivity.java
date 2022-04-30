package com.example.felizmente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felizmente.beans.User;
import com.example.felizmente.db.ControladorDB;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class RegistrationActivity extends AppCompatActivity {

    ControladorDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        db = new ControladorDB(this);
    }

    // CUANDO AÑADAMOS EL HASH TENIA THROWS NOSUCHALGORYTHMEXCEPTION Y INVALIDKEYSPECEXCEPTION

    public void addUser(View view){

        EditText emailInput = findViewById(R.id.userEmailAddress);
        String userEmail = emailInput.getText().toString().toLowerCase();

        EditText passwordInput = findViewById(R.id.userPassword);
        String password = passwordInput.getText().toString();

        boolean exists = db.userExists(userEmail);

        if(userEmail.isEmpty()) {
            emailInput.setError("Falta el usuario (email)");
            emailInput.requestFocus();
        }
        if(password.isEmpty()){
            passwordInput.setError("Falta la contraseña");
            passwordInput.requestFocus();
        }
        if(!userEmail.isEmpty() && !password.isEmpty()) {
            if (!validateEmail(userEmail)) {
                emailInput.setError("Formato de email erróneo.");
                emailInput.setText("");
            } else if (!validatePassword(password)) {
                passwordInput.setError("La contraseña debe tener entre 8 y 20 caracteres e " +
                        "incluir al menos un número, una mayúscula, una minúscula y uno de estos " +
                        "caracteres: @#$%");
                passwordInput.setText("");
            } else {
                if (!exists) {
                    //password = hashPassword(password);
                    db.addUser(userEmail, password);
                    Toast.makeText(this, "Usuario registrado correctamente.", Toast.LENGTH_LONG).show();
                    // ************************************************************ HOLIIIIII LAURENCEEEEEEE!!! Esto es la parte de la transición al Main***************************************************************************
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    emailInput.setText("");
                    passwordInput.setText("");
                    emailInput.requestFocus();
                    Toast.makeText(this, "Ese nombre de usuario ya existe.", Toast.LENGTH_LONG).show();
                }
            }
        }

        // ESTARIA GUAY SI PUDIESEMOS AÑADIR UN CHECKBOX PARA MANTERNERSE LOGGEADO O SI LA APP LO HICIESE AUTOMATICAMENTE (COMPROBAR ESTO)
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
    // lo he cambiado porque se supone que esto sería mas seguro
    // para comprobar que el password coincide se hashea el password que recibamos y se comprueba que coincida
    // el salt es como un paso extra para que se tarde mas en averiguar un password pero ya esta
    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return new String(hash);
    }

//    public String hashPassword(String password) throws NoSuchAlgorithmException {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//
//        MessageDigest md = MessageDigest.getInstance("SHA-512");
//        md.update(salt);
//
//        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
//
//        return new String(hashedPassword);
//    }

    //  Sacado de https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
    public boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        return mat.matches();
    }

    // ************************************ LAURENCE, CREO QUE ESTO SE PODRIA BORRAR, ES LO QUE HABÍA HECHO PARA EL JSON, Y TAMBIEN SE PODRIA BORRAR EL USER QUE ESTÁ EN EL PACKAGE BEANS *******************************************
//    public User createUser(String username, String email, String password){
//        User user = new User();
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setPassword(password);
//        return user;
//    }
//
//    public String writeUserToJson(User user){
//        StringWriter result=new StringWriter();
//        JsonWriter json=new JsonWriter(result);
//
//        try {
//            json.beginObject();
//            json.name("USERNAME").value(user.getUsername());
//            json.name("EMAIL").value(user.getEmail());
//            json.name("CONTRASEÑA").value(user.getPassword());
//            json.endObject();
//            json.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return(json.toString());
//    }
}