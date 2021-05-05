package com.example.reminders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements LoginUser.onLoginListener, SignUpUser.onSignUpListener {
    EditText email, password;
    Button login, register;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        login.setOnClickListener(v -> {
            String emString = email.getText().toString();
            String pwString = password.getText().toString();

            if(TextUtils.isEmpty(emString)) {
                email.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(pwString)){
                password.setError("Password is required");
                return;
            }

            LoginUser bgTask = new LoginUser(emString, pwString, this);
            bgTask.execute();
        });

        register.setOnClickListener(v -> {
            String emString = email.getText().toString();
            String pwString = password.getText().toString();

            if(TextUtils.isEmpty(emString)) {
                email.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(pwString)){
                password.setError("Password is required");
                return;
            }
            if(pwString.length() < 8){
                password.setError("Password must be at least 8 characters");
                return;
            }

            SignUpUser bgTask = new SignUpUser(emString, pwString, this);
            bgTask.execute();
        });
    }

    @Override
    public void onLogin(Boolean userExists) {
        if(userExists) {
            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            Toast.makeText(Login.this, "Login unsuccessful", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSignUp(Boolean userExists) {
        if(userExists) {
            Toast.makeText(Login.this, "Registration successful", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            Toast.makeText(Login.this, "Registration unsuccessful", Toast.LENGTH_LONG).show();
        }
    }
}