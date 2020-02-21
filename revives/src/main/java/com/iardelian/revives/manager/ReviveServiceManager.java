/**
 * Copyright 2020 Ivan Ardelian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.iardelian.revives.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iardelian.revives.model.ReviveServiceController;
import com.iardelian.revives.ReviveServiceNotifier;
import com.iardelian.revives.model.ServiceNotification;
import com.iardelian.revives.service.ReviveService;
import com.iardelian.revives.utils.Utils;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Main managing class. Used to save and use {@link ReviveServiceController} and {@link ServiceNotification} settings.
 * Trigger {@link ReviveServiceNotifier} interface when service starts and stops and provides service status checking.
 *
 * @author Ivan Ardelian
 *
 */
public class ReviveServiceManager {

    private final static String LOG_TAG = ReviveServiceManager.class.getSimpleName();

    /**
     * Default time for service checking
     */
    private long restartCheckingTime = 60000;

    private static final Object SINGLETON_LOCK = new Object();
    @Nullable
    private static volatile ReviveServiceManager sInstance = null;
    private Context mContext;

    private ReviveServiceNotifier serviceNotifier;
    private ServiceNotification serviceNotification;

    private ReviveServiceController restartServiceController;

    /**
     * Gets instance of ReviveServiceManager for application.
     *
     * @param context
     * @return instance of ReviveServiceManager
     */
    @NonNull
    public static ReviveServiceManager getInstanceForApplication(@NonNull Context context) {
        ReviveServiceManager instance = sInstance;
        if (instance == null) {
            Object var2 = SINGLETON_LOCK;
            synchronized (SINGLETON_LOCK) {
                instance = sInstance;
                if (instance == null) {
                    sInstance = instance = new ReviveServiceManager(context);
                }
            }
        }
        return instance;
    }

    private ReviveServiceManager(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * Callbacks initialization.
     *
     * @param serviceNotifier initialization of {@link ReviveServiceNotifier}
     */
    public void initCallbacks(final ReviveServiceNotifier serviceNotifier) {
        this.serviceNotifier = serviceNotifier;
    }

    /**
     * Call {@link ReviveServiceNotifier} interface when service starts or stops
     *
     * @param start boolean
     */
    public void callServiceStart(boolean start){
        if(start){
            serviceNotifier.reviveServiceStarted();
        }else{
            serviceNotifier.reviveServiceStopped();
        }
    }

    /**
     * Checks if service is already running in background and if not - start it.
     * Also installs next service status check after a specified time
     *
     */
    public void startService() {
        //Log.e(LOG_TAG, "isServiceRunningForeground: " + Utils.isServiceRunningForeground(mContext));
        ServiceNotification servNotification = this.serviceNotification;

        if(servNotification == null){
            Log.w(LOG_TAG, "Have you already initialized ServiceNotification? Default will be used");
            servNotification = ServiceNotification.Builder().build();
        }

        if (!Utils.isServiceRunningForeground(mContext)) {
            Intent serviceIntent = new Intent(mContext, ReviveService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForegroundService(serviceIntent);
            } else {
                mContext.startService(serviceIntent);
                NotificationManager nm = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                Notification notification = servNotification.getNotification(mContext);
                notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
                nm.notify(getNotificationID(), notification);
            }
        }
        RestartJobHelper.setCheckingAlarm(mContext);
    }

    /**
     * Gets checking start time in milliseconds.
     *
     * @return checking start time in milliseconds
     */
    long getCheckingRestartTimeMs() {
        return restartCheckingTime;
    }

    /**
     * Sets status check time in milliseconds.
     *
     * @param restartCheckingTime restart checking time in milliseconds
     */
    public void setStatusCheckTime(long restartCheckingTime) {
        if(restartCheckingTime > 0) {
            this.restartCheckingTime = restartCheckingTime;
        }
    }

    /**
     * Gets service notification.
     *
     * @return {@link ServiceNotification}
     */
    public ServiceNotification getServiceNotification() {
        return this.serviceNotification;
    }

    /**
     * Sets service notification.
     *
     * @param serviceNotification {@link ServiceNotification}
     */
    public void setServiceNotification(ServiceNotification serviceNotification) {
        this.serviceNotification = serviceNotification;
    }


    /**
     * Gets notification ID.
     *
     * @return notification ID
     */
    private int getNotificationID() {
        return this.serviceNotification.getNotificationID();
    }

    /**
     * Gets restart service controller.
     *
     * @return  {@link ReviveServiceController}
     */
    public ReviveServiceController getRestartServiceController() {
        return restartServiceController;
    }

    /**
     * Sets restart service controller.
     *
     * @param restartServiceController {@link ReviveServiceController}
     */
    public void setRestartServiceController(ReviveServiceController restartServiceController) {
        this.restartServiceController = restartServiceController;
    }

}
