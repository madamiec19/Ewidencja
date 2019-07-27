package com.example.ewidencja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ewidencja.Database.Move;
import com.example.ewidencja.Database.MoveViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveActivity extends AppCompatActivity{

    public static final int ADD_MOVE_REQUEST = 1;
    private MoveViewModel moveViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Ruchy");

        FloatingActionButton btnAddMove = findViewById(R.id.floatingAddMove);
        btnAddMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoveActivity.this, AddItem.class);
                startActivityForResult(intent, ADD_MOVE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MoveAdapter moveAdapter = new MoveAdapter();
        recyclerView.setAdapter(moveAdapter);

        moveViewModel = ViewModelProviders.of(this).get(MoveViewModel.class);
        moveViewModel.getAllMoves().observe(this, new Observer<List<Move>>() {
            @Override
            public void onChanged(List<Move> moves) {
                moveAdapter.setMoves(moves);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                moveViewModel.delete(moveAdapter.getMoveAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MoveActivity.this, "Ruch usunięty", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MOVE_REQUEST && resultCode == RESULT_OK){
            String car = data.getStringExtra(AddItem.EXTRA_CAR);
            int kmStart = data.getIntExtra(AddItem.EXTRA_KMSTART, 1);
            int kmStop = data.getIntExtra(AddItem.EXTRA_KMSTOP, 1);
            boolean[] types = data.getBooleanArrayExtra(AddItem.EXTRA_TYPES);
            String route = data.getStringExtra(AddItem.EXTRA_ROUTE);
            String driver = "Michał Adamiec"; //TODO! zrobić logowanie
            String comment = data.getStringExtra(AddItem.EXTRA_COMMENT);

            Move move = new Move(car, kmStart, kmStop, types, driver, route, comment );
            addItemToSheet(move);
            moveViewModel.insert(move);
        } else{
            Toast.makeText(this,"Ruch niezapisany", Toast.LENGTH_SHORT).show();
        }
    }

    private void addItemToSheet(final Move inputMove){
        Toast.makeText(getApplicationContext(), "Ruch zapisany " + inputMove.getValue(), Toast.LENGTH_SHORT).show();
        /*final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "gsheet url here",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("car", inputMove.getCar());
                parmas.put("kmStart", Integer.toString(inputMove.getKmStart()));
                parmas.put("kmStop", Integer.toString(inputMove.getKmStop()));
                parmas.put("route", inputMove.getRoute());
                parmas.put("type", inputMove.getMoveTypesString());
                parmas.put("driver", inputMove.getDriver());
                parmas.put("value", Double.toString(inputMove.getValue()));
                if(inputMove.getComment()==null)
                    parmas.put("comment", "brak");
                else parmas.put("comment", inputMove.getComment());

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.move_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_moves:
                moveViewModel.deleteAllMoves();
                Toast.makeText(this, "Wszystkie ruchy usunięte", Toast.LENGTH_SHORT).show();
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
