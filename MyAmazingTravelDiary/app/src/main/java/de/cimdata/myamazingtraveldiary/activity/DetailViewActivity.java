package de.cimdata.myamazingtraveldiary.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import de.cimdata.myamazingtraveldiary.R;

public class DetailViewActivity extends AppCompatActivity {

    private TextView headline;
    private TextView description;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        headline = (TextView)findViewById(R.id.view_headline);
        description = (TextView)findViewById(R.id.view_description);
        imageView = (ImageView)findViewById(R.id.view_img);

    }
}
