package com.example.ewidencja.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ewidencja.Database.Move;
import com.example.ewidencja.Dialogs.CommentDialog;
import com.example.ewidencja.Dialogs.ExtrasDialog;
import com.example.ewidencja.Dialogs.HourPickerDialog;
import com.example.ewidencja.Dialogs.MiddlepointDialog;
import com.example.ewidencja.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMoveActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener, CommentDialog.CommentDialogListener, ExtrasDialog.ExtrasDialogListener, MiddlepointDialog.MiddlepointDialogListener {


    public static final String EXTRA_CAR = "com.example.ewidencja.EXTRA_CAR";
    public static final String EXTRA_TYPES= "com.example.ewidencja.EXTRA_TYPES";
    public static final String EXTRA_KMSTART= "com.example.ewidencja.EXTRA_KMSTART";
    public static final String EXTRA_KMSTOP= "com.example.ewidencja.EXTRA_KMSTOP";
    public static final String EXTRA_ROUTE = "com.example.ewidencja.EXTRA_ROUTE";
    public static final String EXTRA_COMMENT = "com.example.ewidencja.EXTRA_COMMENT";
    public static final String EXTRA_HOUR = "com.example.ewidencja.EXTRA_HOUR";
    public static final String EXTRA_DATE = "com.example.ewidencja.EXTRA_DATE";

    CheckBox cb0, cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    EditText etCar, etKmStart, etKmStop, etPlaceSart, etPlaceStop;
    Button btnAddComment, btnAddExtras, btnAddMove;
    TextView tvDate, tvHour, tvMiddlePoint;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private String moveComment, moveMiddlePoint;
    private int moveExtrasValue; //PRZYGOTOWANIE POD EXTRASY DO RUCHÓW !TODO
    private Move inputMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        moveComment = null;
        moveExtras = null;
        moveMiddlePoint = null;
        moveExtrasValue = 0;
        initTextView();
        initDate();
        initCheckbox();
        initEditText();
        initButton();

    }

    private void initTextView() {
        tvMiddlePoint = (TextView) findViewById(R.id.tvAddMiddlePoint);
        tvMiddlePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiddlepointDialog middlepointDialog = new MiddlepointDialog();
                middlepointDialog.show(getSupportFragmentManager(), "middlepoint dialog");
            }
        });

    }

    private void initDate() {

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);

                String currentDateString = DateFormat.getDateInstance().format(c.getTime());

                TextView tvDate = findViewById(R.id.tvDate);
                tvDate.setText(currentDateString);
            }
        };


        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String sMonth, sDay;
        tvDate = (TextView) findViewById(R.id.tvDate);

        String currentDateString = DateFormat.getDateInstance().format(cal.getTime());
        tvDate.setText(currentDateString);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddMoveActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener ,year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        tvHour = (TextView) findViewById(R.id.tvHour);
        tvHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNumberPicker(view);
            }
        });

    }

    private void initEditText() {
        etCar = (EditText) findViewById(R.id.etCar);
        etKmStart = (EditText) findViewById(R.id.etKmStart);
        etKmStop = (EditText) findViewById(R.id.etKmStop);
        etPlaceSart = (EditText) findViewById(R.id.etPlaceStart);
        etPlaceStop = (EditText) findViewById(R.id.etPlaceStop);
    }

    private void initButton() {

        btnAddMove = (Button) findViewById(R.id.btnAddMove);
        btnAddMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etPlaceStop.getText().toString().isEmpty() || etPlaceSart.getText().toString().isEmpty()
                        || etCar.getText().toString().isEmpty() || etKmStart.getText().toString().isEmpty() || etKmStop.getText().toString().isEmpty()){
                    Toast.makeText(AddMoveActivity.this, "ej ej wypełnij pola", Toast.LENGTH_LONG).show();
                }
                else {
                    Move move;
                    String route;
                    boolean[] moveTypes;
                    moveTypes = new boolean[10];
                    moveTypes[0] = cb0.isChecked();
                    moveTypes[1] = cb1.isChecked();
                    moveTypes[2] = cb2.isChecked();
                    moveTypes[3] = cb3.isChecked();
                    moveTypes[4] = cb4.isChecked();
                    moveTypes[5] = cb5.isChecked();
                    moveTypes[6] = cb6.isChecked();
                    moveTypes[7] = cb7.isChecked();
                    moveTypes[8] = cb8.isChecked();
                    moveTypes[9] = cb9.isChecked();

                    if(moveMiddlePoint==null) route = etPlaceSart.getText().toString() + " --> " + etPlaceStop.getText().toString();
                    else route = etPlaceSart.getText().toString() + " -> " + moveMiddlePoint + " -> " + etPlaceStop.getText().toString();
                    move = new Move(etCar.getText().toString(), tvHour.getText().toString(), tvDate.getText().toString(), Integer.parseInt(etKmStart.getText().toString()),
                            Integer.parseInt(etKmStop.getText().toString()), moveTypes, "Michał Adamiec", route, moveComment);

                    inputMove = move;
                   // addItemToSheet();
                    addItemToDb();

                }}});





        /////////////////////////////////////////////////////////////////////////////////////////
        btnAddComment = (Button) findViewById(R.id.btnAddComment);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog commentDialog = new CommentDialog();
                commentDialog.show(getSupportFragmentManager(), "comment dialog");
            }
        });


        //////////////////////////////////////////////////////////////////////////////////////////
        btnAddExtras = (Button) findViewById(R.id.btnAddExtras);
        btnAddExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtrasDialog extrasDialog =  new ExtrasDialog();
                extrasDialog.show(getSupportFragmentManager(), "extras dialog");
            }
        });}

    private void initCheckbox(){
        cb0 = (CheckBox) findViewById(R.id.cb0);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        cb5 = (CheckBox) findViewById(R.id.cb5);
        cb6 = (CheckBox) findViewById(R.id.cb6);
        cb7 = (CheckBox) findViewById(R.id.cb7);
        cb8 = (CheckBox) findViewById(R.id.cb8);
        cb9 = (CheckBox) findViewById(R.id.cb9);
    }

    public void showNumberPicker(View view){
        HourPickerDialog newFragment = new HourPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        tvHour.setText("GODZINA: " + numberPicker.getValue() );
    }

    @Override
    public void applyComment(String comment) {
        moveComment = comment;

        TextView tvComment = (TextView) findViewById(R.id.tvComment);
        if(moveComment == "")
            tvComment.setText("");
        else
            tvComment.setText("Uwagi: " + moveComment);

    }

    @Override
    public void applyExtras(String extras, int extrasValue) {
        moveExtras = extras;
        moveExtrasValue = extrasValue;
    }

    @Override
    public void applyMiddlepoint(String middlepoint) {
        String pointString = "+" + middlepoint;
        tvMiddlePoint.setText(pointString);
        moveMiddlePoint = middlepoint;
    }

    private void addItemToDb(){
        Intent data = new Intent();
        data.putExtra(EXTRA_CAR, inputMove.getCar());
        data.putExtra(EXTRA_KMSTART , inputMove.getKmStart());
        data.putExtra(EXTRA_KMSTOP, inputMove.getKmStop());
        data.putExtra(EXTRA_TYPES, inputMove.getMoveTypes());
        data.putExtra(EXTRA_ROUTE, inputMove.getRoute());
        data.putExtra(EXTRA_COMMENT, inputMove.getComment());
        data.putExtra(EXTRA_HOUR, inputMove.getHour());
        data.putExtra(EXTRA_DATE, inputMove.getDate());


        // PO CHUJ TO TU TO JEST EWIDENTNIE ZJEBANE COS PRZEKOMBINOWANE TAK POWIEM JAK W #1 !TODO
        setResult(RESULT_OK, data);
        finish();
    }
}
