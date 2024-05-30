package com.example.chessproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddChessPlayer extends AppCompatActivity {
    private Button addNewPlayer;
    private EditText namePlayer;
    private EditText surnamePlayer;
    private EditText ratingPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chess_player);

        addNewPlayer = findViewById(R.id.add_new_player);
        namePlayer = findViewById(R.id.name_player);
        surnamePlayer = findViewById(R.id.surname_player);
        ratingPlayer = findViewById(R.id.rating_player);

        long id = getIntent().getLongExtra("id", 0);

        addNewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameP = namePlayer.getText().toString();
                String surnameP = surnamePlayer.getText().toString();
                double ratingP = Double.parseDouble(ratingPlayer.getText().toString());
                ChessPlayer cp = new ChessPlayer(nameP, surnameP, ratingP, id);
                Database db = new Database(AddChessPlayer.this);
                db.addChessPlayer(cp);
                finish();
            }
        });

    }
}