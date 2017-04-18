package de.cimdata.myamazingtraveldiary.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import de.cimdata.myamazingtraveldiary.R;

public class DetailImageviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_imageview);
        ImageView imageView = (ImageView)findViewById(R.id.imagedetail_image);
        imageView.setImageResource(R.drawable.thailand);
        Button button = (Button)findViewById(R.id.imagedetail_btn);
        button.setText("take image");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordImageActivity.class);
                intent.putExtra("openCam", true);
                startActivity(intent);
            }
        });
    }
}
