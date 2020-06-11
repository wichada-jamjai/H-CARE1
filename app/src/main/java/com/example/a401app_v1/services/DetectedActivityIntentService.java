package com.example.a401app_v1.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.a401app_v1.utils.Constant;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;
public class DetectedActivityIntentService extends IntentService {
    protected static final String TAG = DetectedActivityIntentService.class.getSimpleName();
    public  DetectedActivityIntentService(){
        super(TAG);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // Log.d(TAG,TAG + "onCreate()");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,TAG + "onHandleIntent()");
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

        // Get the list of the probable activities associated with the current state of the
        // device. Each activity is associated with a confidence level, which is an int between
        // 0 and 100.

        List<DetectedActivity> detectedActivities = result.getProbableActivities();

        for (DetectedActivity activity : detectedActivities) {
            //Log.d(TAG, "Detected activity: " + activity.getType() + ", " + activity.getConfidence());
            broadcastActivity(activity);
        }
    }
    private void broadcastActivity(DetectedActivity activity) {
        // Log.d(TAG,TAG+ "broadcastActivity()");
        Intent intent = new Intent(Constant.BROADCAST_DETECTED_ACTIVITY);
        intent.putExtra("type", activity.getType());
        intent.putExtra("confidence", activity.getConfidence());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
