package de.cimdata.myamazingtraveldiary.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.activity.DetailImageviewActivity;
import de.cimdata.myamazingtraveldiary.activity.DetailTextActivity;
import de.cimdata.myamazingtraveldiary.activity.DetailViewActivity;
import de.cimdata.myamazingtraveldiary.activity.MATDiaryMainActivity;

public class PrimaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv1, tv2, tv3, tv4;
    private ImageView imageView;
    Activity act;

    private RecyclerView recyclerView;

    public PrimaryViewHolder(View v, Activity act) {
        super(v);
//        tv1 = (TextView) v.findViewById(R.id.location);
//        tv2 = (TextView) v.findViewById(R.id.description);
//        tv3 = (TextView) v.findViewById(R.id.weather);
//        tv4 = (TextView) v.findViewById(R.id.temperature);
//        imageView = (ImageView) v.findViewById(R.id.img_view1);

        tv1 = (TextView)v.findViewById(R.id.card_location);
        tv2 = (TextView)v.findViewById(R.id.card_desc);
        tv3 = (TextView)v.findViewById(R.id.card_weather);
        tv4 = (TextView)v.findViewById(R.id.card_temperature);
        imageView = (ImageView)v.findViewById(R.id.card_img);

        recyclerView = (RecyclerView) act.findViewById(R.id.mainRecylerView);

        this.act = act;

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    public void setActivity(Activity act) {
        this.act = act;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location:
                Toast.makeText(act, "not implemented yet", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), DetailTextActivity.class);
//                act.startActivity(intent);
                break;
            case R.id.description:
                Toast.makeText(act, "not implemented yet", Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(v.getContext(), DetailTextActivity.class);
//                act.startActivity(intent1);
                break;
            case R.id.img_view1:
                Toast.makeText(act, "not implemented yet", Toast.LENGTH_SHORT).show();
//                Intent intent2 = new Intent(v.getContext(), DetailViewActivity.class);
//                act.startActivity(intent2);
                break;
            default:
                break;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTv1() {
        return tv1;
    }

    public void setTv1(TextView tv1) {
        this.tv1 = tv1;
    }

    public TextView getTv2() {
        return tv2;
    }

    public void setTv2(TextView tv2) {
        this.tv2 = tv2;
    }

    public TextView getTv3() {
        return tv3;
    }

    public void setTv3(TextView tv3) {
        this.tv3 = tv3;
    }

    public TextView getTv4() {
        return tv4;
    }

    public void setTv4(TextView tv4) {
        this.tv4 = tv4;
    }

}