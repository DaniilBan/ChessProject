package com.example.chessproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddNewTournament extends AppCompatActivity {
    private Button addTournamentBut;
    private CalendarView tourDate;
    private TextView dateText;
    private EditText tourName;

    private Calendar calendar;
    private long millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tournament);

        addTournamentBut = findViewById(R.id.add_one_tournament_button);
        tourName = findViewById(R.id.tournament_name_edit);
        tourDate = findViewById(R.id.calendar_date);
        dateText = findViewById(R.id.tournament_name_text);
        calendar = Calendar.getInstance();


        tourDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateText.setText(Integer.toString(dayOfMonth) + "-" + Integer.toString(month) + "-" + Integer.toString(year));
                setDate(year, month, dayOfMonth);
            }
        });

        addTournamentBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tName = tourName.getText().toString();
                Tournament t = new Tournament(tName, millis);
                Database db = new Database(AddNewTournament.this);
                db.addTournament(t);
                finish();
            }
        });
    }

    public void setDate(int year, int month, int day){
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        millis = calendar.getTimeInMillis();
    }
}
