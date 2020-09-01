package com.fullstackoasis.revolvingimagesscreensaver;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getCanonicalName();
    private static int TIMEOUT = 7;
    private Handler waitHandler;
    private ImageTracker imageTracker = new ImageTracker();
    private static int IMAGE_NUM = 0;
    private static int[] images = {R.drawable.a_lost_creek_lake, R.drawable.b_bamiyan_afghanistan,
        R.drawable.c_gakona_alaska, R.drawable.d_willamette_falls };
    private boolean stopped = false;

    /**********************************************************************************************
     * Lifecycle methods
     **********************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTimer();
        Log.d(TAG, "Finished with onCreate method");
    }

    /**
     * If the Activity starts, let revolving images continue.
     */
    @Override
    protected void onStart() {
        super.onStart();
        stopped = false;
        ImageView iv = this.findViewById(R.id.imageView);
        Log.d(TAG, "Finished with onStart method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopped = false;
        Log.d(TAG, "Finished with onResume method");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Finished with onPause method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Finished with onStop method");
        // Activity is no longer visible. Stop the Handler, and
        waitHandler.removeCallbacksAndMessages(null);
        waitHandler = null;
        stopped = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Finished with onDestroy method");
    }

    private void setTimer() {
        if (stopped) return;
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
        if (stopped) return;
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
            if (!stopped) {
                Log.d(TAG, "run!");
                MainActivity.this.showNextImage();
            }
        }
    }

}
