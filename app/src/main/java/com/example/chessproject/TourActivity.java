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

import java.util.List;

public class TourActivity extends AppCompatActivity {
    private static final String getLongId = "id";
    private static final String putLongId = "id";
    private static final String getStringName = "name";
    private static final String putStringName = "name";
    private Button addPlayer;
    private Button Start;
    private ListView playersList;
    public long id;
    public  String str;

    private ChessPlayersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        str = getIntent().getStringExtra(getStringName);
        id = getIntent().getLongExtra(getLongId, 0);
        TextView textID = findViewById(R.id.ID_text);
        textID.setText(str);

        playersList = findViewById(R.id.chess_players);
        addPlayer = findViewById(R.id.add_players_btn);
        Start = findViewById(R.id.start_btn);
        adapter = new ChessPlayersAdapter(this);
        playersList.setAdapter(adapter);

        playersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long lid) {
                Database db = new Database(TourActivity.this);
                ChessPlayer cp = (ChessPlayer)adapter.getItem(position);
                db.deleteChessPlayer(cp.getId());
                refreshList(id);
                return true;
            }
        });

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourActivity.this, AddChessPlayer.class);
                intent.putExtra(putLongId, id);
                startActivity(intent);

            }
        });

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourActivity.this, TourManager.class);
                intent.putExtra(putLongId, id);
                intent.putExtra(putStringName, str);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList(id);
    }

    private void refreshList(long id) {
        new RefreshTask(this, id).execute();
    }

    private static class RefreshTask extends AsyncTask<Void, Void, List<ChessPlayer>> {
        private TourActivity ta;
        private long id;

        public RefreshTask(TourActivity ta, long id) {
            this.ta = ta;
            this.id = id;
        }

        @Override
        protected List<ChessPlayer> doInBackground(Void... voids) {
            Database db = new Database(ta);
            return db.getPlayersForTournaments(id);
        }

        protected void onPostExecute(List<ChessPlayer> chessPlayers) {
            ta.adapter.refresh(chessPlayers);
        }
    }
}