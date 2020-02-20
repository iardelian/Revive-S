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
package com.iardelian.revives.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.iardelian.revives.model.ServiceNotification;
import com.iardelian.revives.manager.ReviveServiceManager;

/**
 * Main service class.
 * Used to start foreground service and add foreground service notification provided by ServiceNotification class
 * {@link ServiceNotification#getNotification}
 * Also inform service manager that service has been started and stopped {@link ReviveServiceManager#callServiceStart}
 *
 * @author Ivan Ardelian
 *
 */
public class ReviveService extends Service {

    private final static String LOG_TAG = ReviveService.class.getSimpleName();
    private ReviveServiceManager superServiceManager;
    private boolean serviceDied = false;

    @Override
    public void onCreate() {
        superServiceManager = ReviveServiceManager.getInstanceForApplication(getApplicationContext());
    }

    /**
     * Starts foreground service with notification
     *
     */
    private void startForegroundIfConfigured() {

        ServiceNotification serviceNotification = superServiceManager.getServiceNotification();

        if (serviceNotification == null) {
            Log.w(LOG_TAG, "Notification settings error. Have you already initialized ServiceNotification? Default will be used");
            serviceNotification = ServiceNotification.Builder().build();
        }

        int notificationID = serviceNotification.getNotificationID();

        Notification sNotification = serviceNotification.getNotification(getApplicationContext());
        if (sNotification != null && notificationID != 0) {
            startForeground(notificationID, sNotification);
            superServiceManager.callServiceStart(true);
        }

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundIfConfigured();
        return START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        Log.w(LOG_TAG, "Service was destroyed");
        stopServiceTasks();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.w(LOG_TAG, "Service task was removed");
        stopServiceTasks();
    }

    /**
     * On some devices service can be stopped with onDestroy() callback on others with onTaskRemoved()
     * callback or calling both callbacks. This method avoid {@link ReviveServiceManager#callServiceStart}
     * to be called multiple times
     */
    private void stopServiceTasks() {
        if (!serviceDied) {
            serviceDied = true;
            superServiceManager.callServiceStart(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true);
            } else {
                stopSelf();
            }
        }
    }
}
