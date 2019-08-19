package com.example.ewidencja.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ewidencja.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText email, password;
    Button button;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;


    // LOGOWANKO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.loginToolbar);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        button = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.loginProgressbar);

        toolbar.setTitle("Logowanie");

        //
        firebaseAuth = FirebaseAuth.getInstance();
        //


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE); // WŁĄCZA ANIMACJE ŁADOWANIA
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // A TU WYŁĄCZA
                                if(task.isSuccessful()) startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                else Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
