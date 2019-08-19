package com.example.ewidencja.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewidencja.Database.MoveViewModel;
import com.example.ewidencja.Dialogs.InitialDialog;
import com.example.ewidencja.Globals;
import com.example.ewidencja.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    LinearLayout moveCard, scheduleCard, hoursCard, routexCard;
    TextView tvDriver;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvDriver = findViewById(R.id.tvDriver);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        tvDriver.setText(firebaseUser.getEmail()); // USTAWIA WYSWIETLANIE MAILA DRAJWERA

        setButtons(); // ustawia dzialanie klikniecia menu
    }


    private void setButtons(){
        moveCard = (LinearLayout) findViewById(R.id.moveCard);
        moveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MoveActivity.class);
                startActivity(intent);
            }
        });

        scheduleCard = (LinearLayout) findViewById(R.id.scheduleCard);
        scheduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://docs.google.com/spreadsheets/d/e/2PACX-1vSFLRj9nVy0gvTdG0dczjBWbRI9D4tSH-wmkLC6x1rTWDRVNkDhzk-ayP_upUD2fOqHuFBF6KpiZfbf/pubhtml?gid=1001224480&single=true";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        hoursCard = (LinearLayout) findViewById(R.id.hoursCard);
        hoursCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddHoursActivity.class);
                startActivity(intent);
            }
        });

        routexCard = findViewById(R.id.routexCard);
        routexCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}
