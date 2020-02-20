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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.iardelian.revives.receiver.StartupBroadcastReceiver;


/**
 * This class used to set periodic messages sending to {@link StartupBroadcastReceiver} with delay that is sets up
 * in {@link ReviveServiceManager#getCheckingRestartTimeMs()}
 *
 * @author Ivan Ardelian
 */
public class RestartJobHelper {

    private static PendingIntent mWakeUpOperation = null;

    /**
     * Use alarm manager to trigger {@link StartupBroadcastReceiver} after a certain time {@link ReviveServiceManager#getCheckingRestartTimeMs()}
     *
     * @param context
     */
    public static void setCheckingAlarm(Context context) {
        long alarmCheckingMs = ReviveServiceManager.getInstanceForApplication(context).getCheckingRestartTimeMs();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + alarmCheckingMs, getCheckingOperation(context));
    }

    /**
     * Gets PendingIntent to trigger {@link StartupBroadcastReceiver}
     * @param context
     * @return PendingIntent
     */
    private static PendingIntent getCheckingOperation(Context context) {
        if (mWakeUpOperation == null) {
            Intent wakeupIntent = new Intent(context, StartupBroadcastReceiver.class);
            wakeupIntent.putExtra("sAlarmIntent", true);
            mWakeUpOperation = PendingIntent.getBroadcast(context, 0, wakeupIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return mWakeUpOperation;
    }

}
