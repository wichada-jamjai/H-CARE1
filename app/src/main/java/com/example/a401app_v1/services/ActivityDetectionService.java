package com.example.a401app_v1.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.a401app_v1.utils.Constant;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class ActivityDetectionService extends Service {
    private static final String TAG = ActivityDetectionService.class.getSimpleName();
    private PendingIntent mPendingIntent;
    private ActivityRecognitionClient mActivityRecognitionClient;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }
    public ActivityDetectionService() {
        //Log.d(TAG, "ActivityDetectionService()");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(getApplicationContext(),"กำลังตรวจจับกิจกรรม..",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStartCommand()");
        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        Intent mIntentService = new Intent(this, DetectedActivityIntentService.class);
        // FLAG_UPDATE_CURRENT indicates that if the described PendingIntent already exists,
        // then keep it but replace its extra data with what is in this new Intent.
        mPendingIntent = PendingIntent.getService(this,
                1, mIntentService, PendingIntent.FLAG_UPDATE_CURRENT);
        requestActivityUpdatesHandler();

        return START_STICKY;
    }
    public void requestActivityUpdatesHandler() {
       // Toast.makeText(getApplicationContext(),"requestActivityUpdatesHandler()",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "requestActivityUpdatesHandler()");
        if(mActivityRecognitionClient != null){
            Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(
                    Constant.DETECTION_INTERVAL_IN_MILLISECONDS,
                    mPendingIntent);

            // Adds a listener that is called if the Task completes successfully.
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void result) {
                   // Toast.makeText(getApplicationContext(),"Successfully requested activity updates",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Successfully requested activity updates");
                }
            });
            // Adds a listener that is called if the Task fails.
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  //  Toast.makeText(getApplicationContext(),"Requesting activity updates failed to start",Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Requesting activity updates failed to start");
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // need to remove the request to Google play services. Brings down the connection.
        removeActivityUpdatesHandler();
    }
    public void removeActivityUpdatesHandler() {
        if(mActivityRecognitionClient != null){
            Task<Void> task = mActivityRecognitionClient.removeActivityUpdates(
                    mPendingIntent);
            // Adds a listener that is called if the Task completes successfully.
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void result) {
                   // Toast.makeText(getApplicationContext(),"Removed activity updates successfully!",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Removed activity updates successfully!");
                }
            });
            // Adds a listener that is called if the Task fails.
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Failed to remove activity updates!",Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to remove activity updates!");
                }
            });
        }
    }

}
