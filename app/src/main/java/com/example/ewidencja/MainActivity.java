package com.example.ewidencja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ewidencja.Database.Move;
import com.example.ewidencja.Database.MoveViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MoveViewModel moveViewModel;

    private LinearLayout moveCard, scheduleCard, hoursCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moveViewModel = ViewModelProviders.of(this).get(MoveViewModel.class);
        moveViewModel.getAllMoves().observe(this, new Observer<List<Move>>() {
            @Override
            public void onChanged(List<Move> moves) {
                //udpate recyclerview
            }
        });


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

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        hoursCard = (LinearLayout) findViewById(R.id.hoursCard);
        hoursCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String url = "https://docs.google.com/forms/d/1wN2KWLLZ55tOjUNZ6LmIJHtbJKrPcUO27AMmeKTcouE/viewform?edit_requested=true";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));*/
                Intent intent = new Intent(getApplicationContext(), AddHours.class);
                startActivity(intent);
            }
        });
    }



}
