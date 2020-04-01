package com.fullstackoasis.revolvingimagesscreensaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getCanonicalName();
    private static int TIMEOUT = 3;
    private Handler waitHandler;
    private ImageTracker imageTracker = new ImageTracker();
    private static int IMAGE_NUM = 0;
    private static int[] images = {R.drawable.a_lost_creek_lake, R.drawable.b_bamiyan_afghanistan,
        R.drawable.c_gakona_alaska, R.drawable.d_willamette_falls };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = this.findViewById(R.id.imageView);
        setTimer();
    }

    private void setTimer() {
        if (waitHandler != null) {
            Log.d(TAG, "setTimer waitHandler was not null");
            // See https://stackoverflow.com/questions/22718951/stop-handler-postdelayed/22719065
            waitHandler.removeCallbacksAndMessages(null);
        } else {
            Log.d(TAG, "setTimer new Handler");
            waitHandler = new Handler();
        }
        Log.d(TAG, "imageTracker will run after " + TIMEOUT*1000 + " ms");
        waitHandler.postDelayed(imageTracker, TIMEOUT*1000);
    }

    protected void showNextImage() {
        Log.d(TAG, "showNextImage going to setTimer");
        ImageView iv = this.findViewById(R.id.imageView);
        iv.setImageDrawable(getNextImage());
        setTimer();
    }

    protected Drawable getNextImage() {
        IMAGE_NUM++;
        if (IMAGE_NUM >= images.length) {
            IMAGE_NUM = 0;
        }
        Log.d(TAG, IMAGE_NUM + "");
        Drawable d = getResources().getDrawable(images[IMAGE_NUM], null);
        return d;
    }

    class ImageTracker implements Runnable {
        public void run() {
            Log.d(TAG, "run!"); MainActivity.this.showNextImage();
        }
    }

}
