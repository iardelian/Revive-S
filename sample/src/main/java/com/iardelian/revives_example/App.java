package com.iardelian.revives_example;

import android.app.Application;
import android.widget.Toast;

import com.iardelian.revives.ReviveServiceNotifier;
import com.iardelian.revives.manager.ReviveServiceManager;
import com.iardelian.revives.model.ReviveServiceController;
import com.iardelian.revives.model.ServiceNotification;


public class App extends Application implements ReviveServiceNotifier {

    @Override
    public void onCreate() {
        super.onCreate();
        ReviveServiceManager reviveServiceManager = ReviveServiceManager.getInstanceForApplication(this);

        reviveServiceManager.initCallbacks(this);
        
        SharedPrefs sp = new SharedPrefs(getApplicationContext());

        ServiceNotification serviceNotification = ServiceNotification.Builder()
                .setTitle(sp.getTitle())
                .setText(sp.getText())
                .setChannelName(sp.getChannelName())
                .setChannelID(sp.getChannelID())
                .setNotificationID(sp.getNotifID())
                .setRequestCode(sp.getRequestCode())
                .setLaunchActivity(MainActivity.class)
                .build();

        ReviveServiceController restartServiceController = ReviveServiceController.Builder()
                .setJobId(sp.getJobID())
                .restartDeadline(sp.getRestartDeadline())
                .restartDelay(sp.getRestartDelay())
                .restartWhenDeviceInUse(sp.getRestartWhenInUse())
                .needInternet(sp.getRestartNeedInternet())
                .needCharging(sp.getRestartNeedCharging())
                .build();

        reviveServiceManager.setServiceNotification(serviceNotification);
        reviveServiceManager.setRestartServiceController(restartServiceController);
        reviveServiceManager.setStatusCheckTime(sp.getServiceRestart());

        reviveServiceManager.startService();
    }

    @Override
    public void reviveServiceStarted() {
        Toast.makeText(getApplicationContext(), getString(R.string.service_started), Toast.LENGTH_LONG).show();
    }

    @Override
    public void reviveServiceStopped() {
        Toast.makeText(getApplicationContext(), getString(R.string.service_stopped), Toast.LENGTH_LONG).show();
    }

}
