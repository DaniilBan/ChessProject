package com.example.chessproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TourManagerAdapter extends BaseAdapter {
    private List<ChessPlayer> dataFirst= new ArrayList<>();
    private List<ChessPlayer> dataSecond= new ArrayList<>();
    private Context ctx;

    public TourManagerAdapter(Context ctx){this.ctx = ctx; }

    public void refresh(List<ChessPlayer> chessPlayers, List<ChessPlayer> chessPlayersSecond) {
        dataFirst.clear();
        dataFirst.addAll(chessPlayers);
        dataSecond.clear();
        dataSecond.addAll(chessPlayersSecond);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataFirst.size();
    }

    @Override
    public Object getItem(int position) {
        return dataFirst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataFirst.get(position).getId();
    }

    public int getCountSecond() {
        return dataSecond.size();
    }

    public Object getItemSecond(int position) {
        return dataSecond.get(position);
    }

    public long getItemIdSecond(int position) {
        return dataSecond.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.tour_manager_adapter, null);
        }

        TextView name = convertView.findViewById(R.id.text_name_for_adapter);
        TextView surname = convertView.findViewById(R.id.text_surname_for_adapter);
        TextView rating = convertView.findViewById(R.id.text_rating_for_adapter);
        TextView points = convertView.findViewById(R.id.text_points_for_adapter);

        TextView nameSecond = convertView.findViewById(R.id.text_name_for_adapter_second);
        TextView surnameSecond = convertView.findViewById(R.id.text_surname_for_adapter_second);
        TextView ratingSecond = convertView.findViewById(R.id.text_rating_for_adapter_second);
        TextView pointsSecond = convertView.findViewById(R.id.text_points_for_adapter_second);

        ChessPlayer cp = dataFirst.get(position);
        ChessPlayer cps = dataSecond.get(position);

        name.setText("" + cp.getName());
        surname.setText("" + cp.getSurname());
        rating.setText("" + cp.getRating());
        points.setText("" + cp.getPoints());

        nameSecond.setText("" + cps.getName());
        surnameSecond.setText("" + cps.getSurname());
        ratingSecond.setText("" + cps.getRating());
        pointsSecond.setText("" + cps.getPoints());

        RadioButton rbDraw = convertView.findViewById(R.id.radiobutton_for_adapter_draw);
        RadioButton rbFirst = convertView.findViewById(R.id.radiobutton_for_adapter);
        RadioButton rbSecond = convertView.findViewById(R.id.radiobutton_for_adapter_second);
        Button bt = convertView.findViewById(R.id.button_for_adapter);
        Database db = new Database(ctx);
        bt.setBackgroundColor(Color.parseColor("#FF0000"));

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbFirst.isChecked()) {db.addPoints(cp.getId(), 1 + cp.getPoints());}
                else if (rbSecond.isChecked()) {db.addPoints(cps.getId(), 1 + cps.getPoints());}
                else if (rbDraw.isChecked()) {
                    db.addPoints(cp.getId(), 0.5 + cp.getPoints());
                    db.addPoints(cps.getId(), 0.5 + cps.getPoints());}
                bt.setBackgroundColor(Color.parseColor("#008000"));
            }
        });

        return convertView;
    }
}
