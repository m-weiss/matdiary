package de.cimdata.myamazingtraveldiary.ui;


import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.model.UserLocations;

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int LOCATION=0;
    Activity act;

    private List<UserLocations> locations;

    public LocationRecyclerViewAdapter(List<UserLocations> locations, Activity act) {
        this.locations = locations;
        this.act=act;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType){

            case LOCATION:
                View v1 = inflater.inflate(R.layout.layout_primary_viewholder, viewGroup, false);
                viewHolder = new PrimaryViewHolder(v1, act);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        switch (viewholder.getItemViewType()){
            case LOCATION:
                PrimaryViewHolder vh1 = (PrimaryViewHolder)viewholder;
                configureViewHolder1(vh1, position);
                break;
            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder)viewholder;
                configureDefaultViewHolder(vh, position);
                break;
        }

    }

    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        vh.getTv1().setText(locations.get(position).toString());
    }

    private void configureViewHolder1(PrimaryViewHolder vh1, int pos){
        UserLocations location = locations.get(pos);
        if(location!=null){
            vh1.getTv1().setText(location.getLocation());
            vh1.getTv2().setText(location.getText());
            vh1.getTv3().setText(location.getWeather());
            vh1.getTv4().setText(location.getTemperature());
            if(location.getImage().equals("img_path"))
                vh1.getImageView().setImageResource(R.drawable.thailand);
            else
                vh1.getImageView().setImageURI(Uri.parse(location.getImage()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(locations.get(position) instanceof UserLocations)
            return LOCATION;
        return -1;
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder{

        private TextView tv1;

        public RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);
            tv1=(TextView)itemView.findViewById(R.id.location);
        }

        public TextView getTv1() {
            return tv1;
        }

        public void setTv1(TextView tv1) {
            this.tv1 = tv1;
        }
    }
}
