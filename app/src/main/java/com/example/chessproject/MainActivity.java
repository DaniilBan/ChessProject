package com.example.chessproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String putLongId = "id";
    private static final String purStringName = "name";

    private ListView tournaments;
    private Button addTornament;
    private TournamentsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tournaments = findViewById(R.id.tournaments_lv);
        addTornament = findViewById(R.id.add_tournaments_btn);

        adapter = new TournamentsAdapter(this);
        tournaments.setAdapter(adapter);

        addTornament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTournament.class);
                startActivity(intent);
            }
        });
        refreshList();

        tournaments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Tournament t = (Tournament)adapter.getItem(position);
                Database db = new Database(MainActivity.this);
                db.deleteTournament(t.getId());
                db.deleteChessPlayerFromTour(t.getId());
                refreshList();
                return true;
            }
        });

        tournaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tournament t = (Tournament)adapter.getItem(position);
                TextView nameTV = findViewById(R.id.tournament_list_name);
                String name = t.getName();
                long idl = t.getId();
                Intent intent = new Intent(MainActivity.this, TourActivity.class);
                intent.putExtra(purStringName, name);
                intent.putExtra(putLongId, idl);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        new RefreshTask(this).execute();
    }

    private static class RefreshTask extends AsyncTask<Void, Void, List<Tournament>> {
        private MainActivity ma;

        public RefreshTask(MainActivity ma) {
            this.ma = ma;
        }

        @Override
        protected List<Tournament> doInBackground(Void... voids) {
            Database db = new Database(ma);
            return db.getAllTournaments();
        }

        protected void onPostExecute(List<Tournament> tournaments) {
            ma.adapter.refresh(tournaments);
        }
    }

    private static class InsertTask extends AsyncTask<Tournament, Void, Void> {

        private MainActivity ma;

        public InsertTask(MainActivity ma) {
            this.ma = ma;
        }

        @Override
        protected Void doInBackground(Tournament... tournaments) {
            Database db = new Database(ma);
            db.addTournament(tournaments[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            ma.refreshList();
        }
    }
}