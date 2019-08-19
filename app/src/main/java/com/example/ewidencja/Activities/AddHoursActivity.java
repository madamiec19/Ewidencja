package com.example.ewidencja.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewidencja.Database.MoveViewModel;
import com.example.ewidencja.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddHoursActivity extends AppCompatActivity {

    private static final String TAG = "AddHoursActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btnCalculate;
    private MoveViewModel moveViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hours);

        moveViewModel = ViewModelProviders.of(this).get(MoveViewModel.class);
        moveViewModel.getValueSum().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                Toast.makeText(getApplicationContext(), "Suma: " + aDouble, Toast.LENGTH_LONG).show();
            }
        });


        btnCalculate = (Button) findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etHour = (EditText) findViewById(R.id.etHour);
                int money = Integer.parseInt(etHour.getText().toString()) * 10;
                TextView tv = (TextView) findViewById(R.id.tvMoney);
                tv.setText(money+"zł (BRUTTO)");
            }
        });




        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddHoursActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener ,year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

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

    }



}
