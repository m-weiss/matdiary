package de.cimdata.myamazingtraveldiary.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import de.cimdata.myamazingtraveldiary.R;

public class RecordImageActivity extends AppCompatActivity {

    private static final String TITLE = "RecordImageActivity";
    private static final String DESRIPTION = "MATD";
    private static final String TAG = RecordImageActivity.class.getSimpleName();
    private static final int IMAGE_CAPTURE=1;
//    private static final int MY_PERMISSIONS_REQUEST =1;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_image);
        if(getIntent().getBooleanExtra("openCam", true))
            startCamera();

        imageView = (ImageView)findViewById(R.id.imagerecord_image);

        Button proceed = (Button)findViewById(R.id.imagerecord_savebtn);
        if(proceed!=null){
            proceed.setText("Keep Image");
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), DetailTextActivity.class);
                    intent.putExtra("newImg", true);
                    intent.putExtra("imgUri", imageUri.toString());
                    startActivity(intent);
                }
            });
        }
        Button btn = (Button)findViewById(R.id.imagerecord_btn);
        if(btn!=null) {
            btn.setText("Take Picture");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startCamera();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    float width = bitmap.getWidth();
                    float height = bitmap.getHeight();

                    int smalHeight = 300;
                    int smallWidth = (int)(width/height*(float)smalHeight);
                    Bitmap smallBitmap = Bitmap.createScaledBitmap(bitmap, smallWidth, smalHeight, false);
                    imageView.setImageBitmap(smallBitmap);
                } catch (IOException e) {
                    Log.e(TAG, "setBitmap()", e);
                    e.printStackTrace();
                }
            }else {
                int rowsdeleted = getContentResolver().delete(imageUri, null,null);
                Log.d(TAG, rowsdeleted + " rows deleted");
            }
        }
    }

    private void startCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, TITLE );
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "test");
        values.put(MediaStore.Images.Media.DESCRIPTION, DESRIPTION);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if(!getIntent().getBooleanExtra("openCam", true)) {
            startActivityForResult(intent, IMAGE_CAPTURE);
        }
        else if(getIntent().getBooleanExtra("openCam", true)){
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, imageUri, this, DetailTextActivity.class);
            startActivityForResult(intent, IMAGE_CAPTURE);
        }
    }
}
