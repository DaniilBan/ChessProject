package com.example.chessproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class Database {
    private static final String DATABASE_NAME = "tournaments.db";
    private static final int DATABASE_Version = 4;
    private static final String TABLE_NAME_TOUR = "ChessTours";
    private static final String COLUMN_ID_TOUR = "id";
    private static final String COLUMN_DATE_TOUR = "date";
    private static final String COLUMN_NAME_TOUR = "Tour";
    private static final String COLUMN_TOUR_TOUR = "tour";

    private static final String TABLE_NAME_PLAYER = "TourPlayers";
    private static final String COLUMN_ID_PLAYER = "id";
    private static final String COLUMN_NAME_PLAYER = "name";
    private static final String COLUMN_SURNAME_PLAYER = "surname";
    private static final String COLUMN_RATING_PLAYER = "rating";
    private static final String COLUMN_ID_TOUR_PLAYER = "id_tour";
    private static final String COLUMN_COLOR_PLAYER = "color";
    private static final String COLUMN_POINTS_PLAYER = "points";
    private static final String COLUMN_PAST_OPPONENTS_PLAYER = "opponents";

    private SQLiteDatabase db;
    public Database(Context ctx)    {
        OpenHelper oh = new OpenHelper(ctx);
        db = oh.getWritableDatabase();
    }

    public void addTournament(Tournament tournament){
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE_TOUR, tournament.getDate());
        cv.put(COLUMN_NAME_TOUR, tournament.getName());

        db.insert(TABLE_NAME_TOUR, null, cv);

    }

    public List<Tournament> getAllTournaments() {
        List<Tournament> answer = new LinkedList<>();
        Cursor cur = db.query(TABLE_NAME_TOUR, null, null, null, null,
                null, null, null);

        cur.moveToFirst();
        if (!cur.isAfterLast()) {
            int idNum = cur.getColumnIndex(COLUMN_ID_TOUR);
            int tourName = cur.getColumnIndex(COLUMN_NAME_TOUR);
            int tourDate = cur.getColumnIndex(COLUMN_DATE_TOUR);


            do {
                long id = cur.getLong(idNum);
                String name = cur.getString(tourName);
                long date = cur.getLong(tourDate);

                answer.add(new Tournament(id, name, date));
            } while (cur.moveToNext());
        }
        cur.close();
        return answer;
    }

    public void deleteTournament(long id) {
        db.delete(TABLE_NAME_TOUR,
                COLUMN_ID_TOUR + " = ?" ,
                new String[]{String.valueOf(id)});
    }


    public void addChessPlayer(ChessPlayer chessPlayer){
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_PLAYER, chessPlayer.getName());
        cv.put(COLUMN_SURNAME_PLAYER, chessPlayer.getSurname());
        cv.put(COLUMN_RATING_PLAYER, chessPlayer.getRating());
        cv.put(COLUMN_ID_TOUR_PLAYER, chessPlayer.getTournament_id());
        cv.put(COLUMN_POINTS_PLAYER, chessPlayer.getPoints());
        cv.put(COLUMN_PAST_OPPONENTS_PLAYER, chessPlayer.getPastOpponents());

        db.insert(TABLE_NAME_PLAYER, null, cv);
    }

    public List<ChessPlayer> getAllChessPlayers() {
        List<ChessPlayer> answer = new LinkedList<>();
        Cursor cur = db.query(TABLE_NAME_PLAYER, null, null, null, null,
                null, null, null);

        cur.moveToFirst();
        if (!cur.isAfterLast()) {
            int idNum = cur.getColumnIndex(COLUMN_ID_PLAYER);
            int playerName = cur.getColumnIndex(COLUMN_NAME_PLAYER);
            int playerRating = cur.getColumnIndex(COLUMN_RATING_PLAYER);
            int playerSurname = cur.getColumnIndex(COLUMN_SURNAME_PLAYER);
            int playerTournamentId = cur.getColumnIndex(COLUMN_ID_TOUR_PLAYER);
            int playerPoints = cur.getColumnIndex(COLUMN_POINTS_PLAYER);
            int playerOpponents = cur.getColumnIndex(COLUMN_PAST_OPPONENTS_PLAYER);


            do {
                long id = cur.getLong(idNum);
                String name = cur.getString(playerName);
                long rating = cur.getLong(playerRating);
                String surname = cur.getString(playerSurname);
                long tournamentId = cur.getLong(playerTournamentId);
                double points = cur.getDouble(playerPoints);
                String opponents = cur.getString(playerOpponents);

                answer.add(new ChessPlayer(id, name, surname, rating, tournamentId, points, opponents));
            } while (cur.moveToNext());
        }
        cur.close();
        return answer;
    }

    public List<ChessPlayer> getPlayersForTournaments(long id){
        List<ChessPlayer> answer = new LinkedList<>();
        Cursor cur = db.query(TABLE_NAME_PLAYER, null, COLUMN_ID_TOUR_PLAYER + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        cur.moveToFirst();
        if (!cur.isAfterLast()) {
            int idNum = cur.getColumnIndex(COLUMN_ID_PLAYER);
            int playerName = cur.getColumnIndex(COLUMN_NAME_PLAYER);
            int playerRating = cur.getColumnIndex(COLUMN_RATING_PLAYER);
            int playerSurname = cur.getColumnIndex(COLUMN_SURNAME_PLAYER);
            int playerTournamentId = cur.getColumnIndex(COLUMN_ID_TOUR_PLAYER);
            int playerPoints = cur.getColumnIndex(COLUMN_POINTS_PLAYER);
            int playerOpponents = cur.getColumnIndex(COLUMN_PAST_OPPONENTS_PLAYER);


            do {
                long idl = cur.getLong(idNum);
                String name = cur.getString(playerName);
                long rating = cur.getLong(playerRating);
                String surname = cur.getString(playerSurname);
                long tournamentId = cur.getLong(playerTournamentId);
                double points = cur.getDouble(playerPoints);
                String opponents = cur.getString(playerOpponents);

                answer.add(new ChessPlayer(idl, name, surname, rating, tournamentId, points, opponents));
            } while (cur.moveToNext());
        }
        cur.close();
        return answer;

    }

    public void addPoints(long id, double points){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POINTS_PLAYER, points);
        db.update(TABLE_NAME_PLAYER, cv, COLUMN_ID_PLAYER + "=" + id, null);
    }

    public void addOpponents(long id, String lid){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PAST_OPPONENTS_PLAYER, lid);
        db.update(TABLE_NAME_PLAYER, cv, COLUMN_ID_PLAYER + "=" + id, null);
    }

//    public double getPoints(long id) {
//        double points = 0;
//        Cursor cur = db.query(TABLE_NAME_PLAYER, null, COLUMN_ID_PLAYER + "=?", new String[]{String.valueOf(id)},
//                null, null, null, null);
//        cur.moveToFirst();
//        if (!cur.isAfterLast()) {
//            int pointsNum = cur.getColumnIndex(COLUMN_POINTS_PLAYER);
//            points = cur.getDouble(pointsNum);
//        }
//        cur.close();
//        return points;
//    }

    public void deleteChessPlayer(long id) {
        db.delete(TABLE_NAME_PLAYER,
                COLUMN_ID_PLAYER + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteChessPlayerFromTour(long tour_id) {
        db.delete(TABLE_NAME_PLAYER,
                COLUMN_ID_TOUR_PLAYER + " = ?",
                new String[]{String.valueOf(tour_id)});
    }







    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String qwerty = "CREATE TABLE " + TABLE_NAME_TOUR + " (" +
                    COLUMN_ID_TOUR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE_TOUR + " LONG, " +
                    COLUMN_NAME_TOUR + " TEXT); ";

            String asd = "CREATE TABLE " + TABLE_NAME_PLAYER + " (" +
                    COLUMN_ID_PLAYER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_PLAYER + " TEXT, " +
                    COLUMN_SURNAME_PLAYER + " TEXT, " +
                    COLUMN_RATING_PLAYER + " LONG, " +
                    COLUMN_ID_TOUR_PLAYER + " LONG, " +
                    COLUMN_PAST_OPPONENTS_PLAYER + " TEXT, " +
                    COLUMN_POINTS_PLAYER + " REAL); ";

            db.execSQL(qwerty);
            db.execSQL(asd);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLAYER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TOUR);
            onCreate(db);

        }

    }
}
