package com.test.weather;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    List<WeatherDataId> weatherDataId = new ArrayList<>();
    Context context;

    public WeatherAdapter(Context ctx, List<WeatherDataId> weatherDataId) {
        this.context = ctx;
        this.weatherDataId = weatherDataId;
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherHolder holder, int position) {

        WeatherDataId album = weatherDataId.get(position);

//        if(weatherDataId.get(position).getCityName() == null){
//
//        }

        holder.tv_item_date.setText(weatherDataId.get(position).getDate());
        holder.tv_item_countryname.setText(weatherDataId.get(position).getCountryName() + ", " +
                weatherDataId.get(position).getCityName() + ": " + weatherDataId.get(position).getTemper());
//        holder.tv_item_cityname.setText(weatherDataId.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return weatherDataId.size();
    }

    public void setData(List<WeatherDataId> users) {
        weatherDataId.clear();
        weatherDataId.addAll(users);
        notifyDataSetChanged();
    }

    static class WeatherHolder extends RecyclerView.ViewHolder { //  implements View.OnClickListener

        CardView cv;
        TextView tv_item_countryname;
        TextView tv_item_date;

        public WeatherHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tv_item_countryname = (TextView) itemView.findViewById(R.id.tv_item_countryname);
            tv_item_date = (TextView) itemView.findViewById(R.id.tv_item_date);
        }
    }
}
