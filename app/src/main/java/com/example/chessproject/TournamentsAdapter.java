package com.example.chessproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TournamentsAdapter extends BaseAdapter {
    private List<Tournament> data = new ArrayList<>();
    private Context ctx;


    public TournamentsAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void refresh(List<Tournament> tournaments){
        data.clear();
        data.addAll(tournaments);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.tornament_list_adapter, null);
        }

        TextView nameTV = convertView.findViewById(R.id.tournament_list_name);
        TextView dateTV = convertView.findViewById(R.id.tournament_list_date);

        Tournament t = data.get(position);

        nameTV.setText("" + t.getName() + " " + t.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String format = formatter.format(t.getDate());
        dateTV.setText("" + format);

        return convertView;
    }
}
