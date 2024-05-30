package com.example.chessproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TourManager extends AppCompatActivity {
    private static final String getLongId = "id";
    private static final String putLongId = "id";
    private static final String getStringName = "name";
    private static final String putStringName = "name";
    private TextView nameTournament;
    private ListView tourChessPlayersPars;
    private Button nextTour;
    private TourManagerAdapter adapter;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_manager);

        nameTournament = findViewById(R.id.name_tournament);
        tourChessPlayersPars = findViewById(R.id.tour_chess_players_pars);
        nextTour = findViewById(R.id.next_tour_btn);

        adapter = new TourManagerAdapter(this);
        tourChessPlayersPars.setAdapter(adapter);

        id = getIntent().getLongExtra(getLongId, 0);
        nameTournament.setText(getIntent().getStringExtra(getStringName));

        Database db = new Database(TourManager.this);
        List<ChessPlayer> chessPlayersList = db.getPlayersForTournaments(id);

//        Collections.sort(chessPlayersList, new Comparator<ChessPlayer>() {
//            @Override
//            public int compare(ChessPlayer o1, ChessPlayer o2) {
//                return Double.compare(o2.getRating(), o1.getRating()).(o2.getPoints(), o1.getPoints());
//            }
//        });

        chessPlayersList.sort(
                Comparator.comparing(ChessPlayer::getPoints).thenComparing(ChessPlayer::getRating)
        );

        List<ChessPlayer> chessPlayersFirst = new ArrayList<>();
        List<ChessPlayer> chessPlayersSecond = new ArrayList<>();

        for (int i = chessPlayersList.size() - 1; i >= 0; i -= 2){
            chessPlayersFirst.add(chessPlayersList.get(i));
            chessPlayersSecond.add(chessPlayersList.get(i - 1));
        }

        adapter.refresh(chessPlayersFirst, chessPlayersSecond);

        nextTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ChessPlayer> chessPlayersList = db.getPlayersForTournaments(id);
                chessPlayersList.sort(
                        Comparator.comparing(ChessPlayer::getPoints).thenComparing(ChessPlayer::getRating)
                );
                chessPlayersFirst.clear();
                chessPlayersSecond.clear();
                for (int i = chessPlayersList.size() - 1; i >= 0; i -= 2){
                    chessPlayersFirst.add(chessPlayersList.get(i));
                    chessPlayersSecond.add(chessPlayersList.get(i - 1));
                }
//                for (int i = chessPlayersList.size() - 1; i >= 0; i -= 1){
//                    nameTournament.setText(nameTournament.getText() + "!");
//                    if (chessPlayersList.get(i) != null) {
//                        for (int j = chessPlayersList.size() - 2; j >= 0; j -= 1) {
//                            nameTournament.setText(nameTournament.getText() + "&");
//                            if (Arrays.asList(chessPlayersList.get(i).getPastOpponents().split(" "))
//                                    .contains(Long.toString(chessPlayersList.get(j).getId())) && chessPlayersList.get(j) != null) {
//                                nameTournament.setText(nameTournament.getText() + "?");
//                                chessPlayersFirst.add(chessPlayersList.get(i));
//                                chessPlayersSecond.add(chessPlayersList.get(j));
//                                db.addOpponents(chessPlayersList.get(i).getId(),
//                                        chessPlayersList.get(i).getPastOpponents() + " " + Long.toString(chessPlayersList.get(j).getId()));
//                                db.addOpponents(chessPlayersList.get(j).getId(),
//                                        chessPlayersList.get(j).getPastOpponents() + " " + Long.toString(chessPlayersList.get(i).getId()));
//                                chessPlayersList.set(i, null);
//                                chessPlayersList.set(j, null);
//                                break;
//                            }
//                        }
//                    }
//                }
                adapter.refresh(chessPlayersFirst, chessPlayersSecond);
            }
        });
    }
}