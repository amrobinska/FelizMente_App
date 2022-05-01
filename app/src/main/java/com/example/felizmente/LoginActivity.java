package com.example.felizmente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.db.ControladorDB;

public class LoginActivity extends AppCompatActivity {

    private ControladorDB db;
    private EditText emailBox, passBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

    }

    // *************************************************************** HOLI LAURENCE! ESTA ES LA TRANSICIÓN QUE SE LE PASA AL BOTON EN EL XML PARA PASAR AL REGISTRATION ACTIVITY CON EL BOTON REGISTRARME
    public void goToRegistration(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    // CUANDO AÑADAMOS EL HASH TENIA THROWS NOSUCHALGORYTHMEXCEPTION Y INVALIDKEYSPECEXCEPTION
    public void login(View view){
        db= new ControladorDB(this);

        emailBox = findViewById(R.id.user);
        String user = emailBox.getText().toString();

        passBox = findViewById(R.id.userPass);
        String pass = passBox.getText().toString();

        if(user.isEmpty()){
            emailBox.setError("Falta el usuario (email)");
            emailBox.requestFocus();
        } else if (pass.isEmpty()){
            passBox.setError("Falta la contraseña");
            passBox.requestFocus();
        } else if (!db.userExists(user)){
            Toast toast = Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_LONG);
            toast.show();
            emailBox.setText("");
            passBox.setText("");
            emailBox.requestFocus();
        } else if(!(db.login(user,pass)).isEmpty()){
//            pass = hashPassword(pass);
            db.login(user,pass);
            // ************************************************************ HOLIIIIII LAURENCEEEEEEE!!! Esto es la parte de la transición al Main***************************************************************************
            startActivity(new Intent(this, MainActivity.class));
            // finish(); // SI LE PONEMOS ESTO ENTONCES SE DESTRUYE EL LOGIN PARA QUE NO SE PUEDA IR PARA ATRÁS
        } else {
            Toast toast = Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_LONG);
            toast.show();
            emailBox.setText("");
            passBox.setText("");
            emailBox.requestFocus();
        }
    }

    // ESTÁ DANDO PROBLEMAS, EN EL REGISTRATION BIEN, PERO EN CUANTO LO METO AQUI PETA LA APP
    // esto está tambien en el registration
//    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        byte[] hash = factory.generateSecret(spec).getEncoded();
//
//        return new String(hash);
//    }
}