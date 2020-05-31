package com.example.corona;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflalter;
    List<String> countries, state;
    List<Integer> confirmed, active, deaths, recovered;
    List<Double> lat, longi;
    public Adapter(Context context, List<String> countries, List<String> state, List<Integer> confirmed,
                   List<Integer> active, List<Integer> deaths, List<Integer> recovered,
                   List<Double> lat, List<Double> longi){
        this.countries = countries;
        this.state = state;
        this.confirmed = confirmed;
        this.active = active;
        this.deaths = deaths;
        this.recovered = recovered;
        this.lat = lat;
        this.longi = longi;
        this.inflalter = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflalter.inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String country = countries.get(position);
        Integer confirm = confirmed.get(position);
        Integer death = deaths.get(position);
        Integer recover = recovered.get(position);
        Integer activ = active.get(position);
        String stte = state.get(position);

//        Log.d("TAG","--------------------------------------------"+country);
//        Log.d("TAG","--------------------------------------------"+confirm);
//        Log.d("TAG","--------------------------------------------"+death);
//        Log.d("TAG","--------------------------------------------"+recover);
//        Log.d("TAG","--------------------------------------------"+activ);
//        Log.d("TAG","--------------------------------------------"+stte);




        holder.countries.setText(country);
        holder.states.setText(stte);
        holder.confirmed.setText(confirm.toString());
        holder.recovered.setText(recover.toString());
        holder.active.setText(activ.toString());
        holder.deaths.setText(death.toString());
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView countries, confirmed, deaths, recovered, active, states;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countries = itemView.findViewById(R.id.countries);
            confirmed = itemView.findViewById(R.id.confirmed);
            deaths = itemView.findViewById(R.id.deaths);
            recovered = itemView.findViewById(R.id.recovered);
            active = itemView.findViewById(R.id.active);
            states = itemView.findViewById(R.id.states);
        }
    }
}
