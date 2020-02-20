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
package com.iardelian.revives.model;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.iardelian.revives.R;

/**
 * This class provides non cancelable notification that is used by service
 * In case if it's not configured it will use default settings
 *
 * @author Ivan Ardelian
 *
 */
public class ServiceNotification {

    private final static String LOG_TAG = ServiceNotification.class.getSimpleName();

    private String notificationTitle = "Revive-S";
    private String notificationText = "untitled";
    private int notificationID = 180194;
    private String channelID = "defaultReviveChannelId";
    private String channelName = "defaultReviveChannelName";
    private int smallIcon = -1;
    private int largeIcon = -1;
    private int requestCode = 2701;
    private Class<?> activity = null;

    private ServiceNotification() {}

    /**
     * Build service notification.
     *
     * @param context
     * @return Notification
     */
    public Notification getNotification(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelID);

        Bitmap largeIconBitmap;

        if (largeIcon != -1) {
            largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), largeIcon);
        } else {
            largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_custom_notif_icon);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                NotificationChannel chan = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                assert manager != null;
                manager.createNotificationChannel(chan);
            } catch (IllegalArgumentException e) {
                Log.w(LOG_TAG, "Invalid notification settings");
                e.printStackTrace();
            }
        }

        mBuilder.setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .setChannelId(channelID);

        if (largeIconBitmap != null) {
            mBuilder.setLargeIcon(largeIconBitmap);
        }

        if (smallIcon != -1) {
            mBuilder.setSmallIcon(smallIcon);
        } else {
            mBuilder.setSmallIcon(R.drawable.ic_custom_notif_icon);
        }

        if (activity != null) {
            mBuilder.setContentIntent(PendingIntent.getActivity(context, requestCode,
                    new Intent(context, activity), PendingIntent.FLAG_CANCEL_CURRENT));
        }

        return mBuilder.build();
    }

    /**
     * Gets notification id.
     *
     * @return notification id
     */
    public int getNotificationID() {
        return this.notificationID;
    }

    /**
     * Builder
     * @return ServiceNotification.Builder
     */
    public static Builder Builder() {
        return new ServiceNotification().new Builder();
    }

    /**
     * Builder class for ServiceNotification objects.
     * Provides a convenient way to set the various fields
     */
    public class Builder {

        private Builder() {
        }

        /**
         * Sets notification title.
         *
         * @param title title.
         */
        public Builder setTitle(String title) {
            if (!TextUtils.isEmpty(title)) {
                ServiceNotification.this.notificationTitle = title;
            } else {
                Log.w(LOG_TAG, "Invalid notification title. Default will be used");
            }
            return this;
        }

        /**
         * Sets notification text.
         *
         * @param notificationText notification text
         */
        public Builder setText(String notificationText) {
            if (!TextUtils.isEmpty(notificationText)) {
                ServiceNotification.this.notificationText = notificationText;
            } else {
                Log.w(LOG_TAG, "Invalid notification text. Default will be used");
            }
            return this;
        }

        /**
         * Sets notification id.
         *
         * @param notificationID notification id
         */
        public Builder setNotificationID(int notificationID) {
            if (notificationID != 0) {
                ServiceNotification.this.notificationID = notificationID;
            } else {
                Log.w(LOG_TAG, "Invalid notification ID value. Default will be used");
            }
            return this;
        }

        /**
         * Sets notification channel id.
         *
         * @param channelID channel id
         */
        public Builder setChannelID(String channelID) {
            if (!TextUtils.isEmpty(channelID)) {
                ServiceNotification.this.channelID = channelID;
            } else {
                Log.w(LOG_TAG, "Invalid channel ID value. Default will be used");
            }
            return this;
        }

        /**
         * Sets notification channel name.
         *
         * @param channelName channel name
         */
        public Builder setChannelName(String channelName) {
            if (!TextUtils.isEmpty(channelName)) {
                ServiceNotification.this.channelName = channelName;
            } else {
                Log.w(LOG_TAG, "Invalid channel name. Default will be used");
            }
            return this;
        }

        /**
         * Sets small icon of notification.
         *
         * @param smallIcon small icon
         */
        public Builder setSmallIcon(int smallIcon) {
            if (smallIcon != 0) {
                ServiceNotification.this.smallIcon = smallIcon;
            } else {
                Log.w(LOG_TAG, "Invalid small notification icon. Default will be used");
            }
            return this;
        }

        /**
         * Sets large icon of notification.
         *
         * @param largeIcon notification large icon
         */
        public Builder setLargeIcon(int largeIcon) {
            if (largeIcon != 0) {
                ServiceNotification.this.largeIcon = largeIcon;
            } else {
                Log.w(LOG_TAG, "Invalid large notification icon. Default will be used");
            }
            return this;
        }

        /**
         * Sets notification request code.
         *
         * @param requestCode notification request code
         */
        public Builder setRequestCode(int requestCode) {
            if (requestCode > 0) {
                ServiceNotification.this.requestCode = requestCode;
            } else {
                Log.w(LOG_TAG, "Invalid request code value. Default will be used");
            }
            return this;
        }

        /**
         * Sets launch activity that will be opened by tapping on notification
         *
         * @param activity activity
         */
        public Builder setLaunchActivity(Class<?> activity) {
            try {
                if (activity.newInstance() instanceof Activity) {
                    ServiceNotification.this.activity = activity;
                } else {
                    Log.w(LOG_TAG, "Please check notification activity settings");
                }
            } catch (IllegalAccessException | InstantiationException e) {
                Log.w(LOG_TAG, "Please check notification activity settings");
                e.printStackTrace();
            }
            return this;
        }

        /**
         * Build service notification.
         *
         * @return ServiceNotification object
         */
        public ServiceNotification build() {
            return ServiceNotification.this;
        }

    }
}
