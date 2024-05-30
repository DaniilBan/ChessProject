package com.example.chessproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChessPlayersAdapter extends BaseAdapter {
    private List<ChessPlayer> data = new ArrayList<>();
    private Context ctx;

    public ChessPlayersAdapter(Context ctx) {this.ctx = ctx;}

    public void refresh(List<ChessPlayer> chessPlayers){
        data.clear();
        data.addAll(chessPlayers);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {return data.size(); }

    @Override
    public Object getItem(int position) {return data.get(position); }

    @Override
    public long getItemId(int position) {return data.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.player_list_adapter, null);
        }

        TextView nameList = convertView.findViewById(R.id.name_player_list);
        TextView surnameList = convertView.findViewById(R.id.surname_player_list);
        TextView ratingList = convertView.findViewById(R.id.rating_player_list);

        ChessPlayer cp = data.get(position);

        nameList.setText("" + cp.getName());
        surnameList.setText("" + cp.getSurname());
        ratingList.setText("" + cp.getRating());

        return convertView;
    }
}
