package de.cimdata.myamazingtraveldiary.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.activity.MATDiaryMainActivity;

/**
 * Startscreen-Activity
 * proceed to Main-Activity by touch/click anywhere, or automatically after a predefined amount of time
 * @author Max Weiss
 */
public class StartScreenActivity extends AppCompatActivity {

    private final static int AUTOPROCEED_SHORT= 1000, AUTOPROCEED_MEDIUM = 5000, AUTOPROCEED_LONG= 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        RelativeLayout frameLayout = (RelativeLayout) findViewById(R.id.mainframe);

        final Runnable autoproceedTask = new Runnable() {
            @Override
            public void run() {
                continueToMainActivity();
            }
        };
        final Handler handler = new Handler();
        handler.postDelayed(autoproceedTask, AUTOPROCEED_MEDIUM);

        if (frameLayout!=null)
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    continueToMainActivity();
                    handler.removeCallbacks(autoproceedTask);
                }
            });
    }

    private void continueToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MATDiaryMainActivity.class);
        startActivity(intent);
    }

}
